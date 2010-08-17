package com.merge.splitpane;

import javax.swing.JSplitPane;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import com.merge.algo.Algorithms;
import com.merge.listeners.ActionEventListeners;
import com.merge.statusbar.AppStatusBar;
import com.merge.undoredofw.UndoableEditEventListener;
import com.merge.util.Config;

public class AppSplitPane extends JSplitPane{

	private static final long serialVersionUID = 1L;
	DataLoader leftLoader = null;
	DataLoader rightLoader = null;
	String fileNameLeft = null;
	String fileNameRight = null;
	Resources leftRes = null;
	Resources rightRes = null;
	TableModel leftModel = null;
	TableModel rightModel = null;
	UndoManager undoManager = null;
	UndoableEditSupport undoSupport = null;
	private int selectedFilter = ActionEventListeners.ALL_FILTER;

	public AppSplitPane(String fileNameLeft, String fileNameRight) {
		super();
		this.fileNameLeft = fileNameLeft;
		this.fileNameRight = fileNameRight;
		leftRes =  new Resources();
		rightRes = new Resources();
		leftRes.setFileName(fileNameLeft);
		rightRes.setFileName(fileNameRight);
		leftRes.setOther(rightRes);
		rightRes.setOther(leftRes);
		runLoaders();
		buildUI();
		if(fileNameLeft != null && fileNameRight != null){
			String fLeft[] = fileNameLeft.split(Config.getProperty("FILE.SEPARATOR"));
			String fRight[] = fileNameRight.split(Config.getProperty("FILE.SEPARATOR"));
			setName(fLeft[fLeft.length - 1]+" - "+fRight[fRight.length - 1]);
		}
		undoManager = new UndoManager();
		undoSupport = new UndoableEditSupport();
		undoSupport.addUndoableEditListener(new UndoableEditEventListener(undoManager));
	}

	private void runLoaders(){
		leftLoader = new DataLoader(fileNameLeft);
		rightLoader =  new DataLoader(fileNameRight);
		leftRes.setLoader(leftLoader);
		rightRes.setLoader(rightLoader);
		AppStatusBar.runTaskWithProgressBar();
		try{
			leftLoader.start();
			rightLoader.start();
			leftLoader.join();
			rightLoader.join();
			Algorithms.matchArrayData(leftLoader.getData(), rightLoader.getData());
		}catch(Exception e){
			e.printStackTrace();
		}
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	private void buildUI(){
		leftModel = new TableModel(leftRes);
		rightModel = new TableModel(rightRes);
		leftRes.setModel(leftModel);
		rightRes.setModel(rightModel);
		setLeftComponent(new LeftPanel(leftRes));
		setRightComponent(new RightPanel(rightRes));
		setResizeWeight(.5);
	}

	public Resources getLeftRes() {
		return leftRes;
	}

	public void setLeftRes(Resources leftRes) {
		this.leftRes = leftRes;
	}

	public Resources getRightRes() {
		return rightRes;
	}

	public void setRightRes(Resources rightRes) {
		this.rightRes = rightRes;
	}

	public int getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(int selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public UndoableEditSupport getUndoSupport() {
		return undoSupport;
	}

	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}

	public void setUndoSupport(UndoableEditSupport undoSupport) {
		this.undoSupport = undoSupport;
	}
}
