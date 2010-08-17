package com.merge.listeners;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.merge.algo.Algorithms;
import com.merge.clipboard.ClipBoardData;
import com.merge.folder.Item;
import com.merge.folder.JCompareWindow;
import com.merge.folder.LeftItem;
import com.merge.folder.RightItem;
import com.merge.folder.menubar.FolderMenuBar;
import com.merge.folder.toolbar.FolderToolBar;
import com.merge.menubar.CheckBoxMenuItem;
import com.merge.menubar.MenuBar;
import com.merge.print.PrintUtilities;
import com.merge.splitpane.AppSplitPane;
import com.merge.splitpane.DataFilter;
import com.merge.splitpane.DataLoader;
import com.merge.splitpane.DataRow;
import com.merge.splitpane.EmptyDataRow;
import com.merge.splitpane.ExitDialog;
import com.merge.splitpane.FindDataBean;
import com.merge.splitpane.FindDialog;
import com.merge.splitpane.GoToDialog;
import com.merge.splitpane.Resources;
import com.merge.splitpane.StringDataRow;
import com.merge.splitpane.TabComponent;
import com.merge.splitpane.TabPane;
import com.merge.splitpane.Table;
import com.merge.splitpane.TableModel;
import com.merge.statusbar.AppStatusBar;
import com.merge.toolbar.ToolBar;
import com.merge.toolbar.ToolBarToggleButton;
import com.merge.undoredofw.CopyPasteRows;
import com.merge.undoredofw.CutPasteRows;
import com.merge.undoredofw.ReplaceRow;
import com.merge.util.Config;


/**
 * This is the main class that will listen to all the action events on 
 * tool bar as well as menu items. It has access to all the resources of the 
 * panes and hence can perform any operation on any component in response to any
 * event
 * @author singhs
 *
 */
public class ActionEventListeners implements ActionListener, ChangeListener, ClipboardOwner {

	private Resources left = null;
	private Resources right = null;
	private Component pane = null;
	private TabPane parent = null;
	private FindDataBean findData = null;

	/* Filters to be used in differences, it was required to maintain state when we change
	 * tabs
	 */
	public static final int ALL_FILTER = 0;
	public static final int DIFF_FILTER = 2;
	public static final int SAME_FILTER = 1;
	/* The find dialog screen */
	FindDialog findDiag = null;
	/* The goto dialog screen */
	GoToDialog goToDiag = null;
	/* Static instance of this class */
	private static ActionEventListeners instance = null;
	private ExitDialog exit = null;
	private JFrame mainWin = null;
	private JTree leftTree;
	private JTree rightTree;
	private JTree selectedTree;
	/* Make constructor private so that no one can create object of this class */
	private ActionEventListeners() {}

	/**
	 * Method to return the instance of the class
	 * @return
	 */
	public static ActionEventListeners getInstance(){
		if(instance == null) {
			instance =  new ActionEventListeners();
		}
		return instance;
	}
	public void setSelectedTree(JTree tree){
		selectedTree = tree;
	}
	public void setTrees(JTree left, JTree right){
		this.leftTree = left;
		this.rightTree = right;
	}

	private String getSelectedFilePath(){
		Item item = (Item)selectedTree.getSelectionPath().getLastPathComponent();
		String path;
		if(item instanceof LeftItem){
			LeftItem lItem = (LeftItem)item;
			System.out.println("Left:"+lItem.getItem().getLeftPath()+File.separator+lItem.getName());
			path = lItem.getItem().getLeftPath()+File.separator+lItem.getName();
		}
		else{
			RightItem lItem = (RightItem)item;
			System.out.println("Right:"+lItem.getItem().getRightPath()+File.separator+lItem.getName());
			path = lItem.getItem().getRightPath()+File.separator+lItem.getName();
		}
		return path;
	}
//	@Override
//	public void valueChanged(TreeSelectionEvent e) {
//		JTree tree = (JTree)e.getSource();
//		Item item = (Item)tree.getSelectionPath().getLastPathComponent();
//		if(item instanceof LeftItem){
//			selectedTree = leftTree;
//			System.out.println("Selected from left tree");
//		}
//		if(item instanceof RightItem){
//			selectedTree = rightTree;
//			System.out.println("Selected from right tree");
//		}
//	}
	/**
	 * Method that handles all the events in application
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		((Component)event.getSource()).setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if(event.getActionCommand().equals("SameFilter")){
			handleSameFilterButton();
			((AppSplitPane)pane).setSelectedFilter(SAME_FILTER);
		}
		else if(event.getActionCommand().equals("DiffFilter")){
			handleDiffFilterButton();
			((AppSplitPane)pane).setSelectedFilter(DIFF_FILTER);
		}
		else if(event.getActionCommand().equals("AllFilter")){
			handleAllFilterButton();
			((AppSplitPane)pane).setSelectedFilter(ALL_FILTER);
		}
		else if(event.getActionCommand().equals("CopyToRight")){
			handleCopyToRight();
			right.setDirty(true);
		}
		else if(event.getActionCommand().equals("CopyToLeft")){
			handleCopyToLeft();
			left.setDirty(true);
		}
		else if(event.getActionCommand().equals("NextDiff")){
			handleGetNextDiff();
		}
		else if(event.getActionCommand().equals("PrevDiff")){
			handleGetPrevDiff();
		}
		else if(event.getActionCommand().equals("Lookup")){
			findDiag = new FindDialog(this);
			findDiag.setVisible(true);
		}
		else if(event.getActionCommand().equals("Find")){
			findData = new FindDataBean(findDiag);
			handleFindString();
		}
		else if(event.getActionCommand().equals("FindNext")){
			findData.setSearchUp(false);
			handleFindString();
		}
		else if(event.getActionCommand().equals("FindPrev")){
			findData.setSearchUp(true);
			handleFindString();
		}
		else if(event.getActionCommand().equals("Cancel")){
			findDiag.setVisible(false);
			findDiag = null;
		}
		else if(event.getActionCommand().equals("GoTo")){
			goToDiag = new GoToDialog();
			goToDiag.setVisible(true);
		}
		else if(event.getActionCommand().equals("GoToLineNumber")){
			handleGoToLineNumber();
		}
		else if(event.getActionCommand().equals("Reload")){
			handleReload();
		}
		else if(event.getActionCommand().equals("SwapPanes")){
			handleSwapPanes();
		}
		else if(event.getActionCommand().equals("ShowLineNumbers")){
			if(event.getSource() instanceof ToolBarToggleButton)
				handleShowLineNumbers(((ToolBarToggleButton)event.getSource()).isSelected());
			if(event.getSource() instanceof CheckBoxMenuItem)
				handleShowLineNumbers(((CheckBoxMenuItem)event.getSource()).isSelected());
		}
		else if(event.getActionCommand().equals("ShowWhiteSpaces")){
			if(event.getSource() instanceof ToolBarToggleButton)
				handleShowWhiteSpaces(((ToolBarToggleButton)event.getSource()).isSelected());
			if(event.getSource() instanceof CheckBoxMenuItem)
				handleShowWhiteSpaces(((CheckBoxMenuItem)event.getSource()).isSelected());
		}
		else if(event.getActionCommand().equals("New")){
			handleNewTab();
		}
		else if(event.getActionCommand().equals("Undo")){
			if(((AppSplitPane)pane).getUndoManager().canUndo())
				((AppSplitPane)pane).getUndoManager().undo();
		}
		else if(event.getActionCommand().equals("Redo")){
			if(((AppSplitPane)pane).getUndoManager().canRedo())
				((AppSplitPane)pane).getUndoManager().redo();
		}
		else if(event.getActionCommand().equals("SelectAll")){
			handleSelectAll();
		}
		else if(event.getActionCommand().equals("Copy")){
			setDataOnClipBoard();
		}
		else if(event.getActionCommand().equals("Paste")){
			getDataFromClipBoard();

		}
		else if(event.getActionCommand().equals("Cut")){
			setDataOnClipBoard();
			removeSelectedData();
		}
		else if(event.getActionCommand().equals("RemoveWS")){
			handleTrimTrailingWhiteSpaces();
		}
		else if(event.getActionCommand().equals("SpaceToTabs")){
			handleLeadingSpaceToTabs();
		}
		else if(event.getActionCommand().equals("TabsToSpaces")){
			handleTabsToSpaces();
		}
		else if(event.getActionCommand().equals("ToUnix")){
			handleConvertWindowsToUnix();
		}
		else if(event.getActionCommand().equals("Save")){
			save();
		}
		else if(event.getActionCommand().equals("SaveAs")){
			saveAs();
		}
		else if(event.getActionCommand().equals("Save Left")){
			save(left);
		}
		else if(event.getActionCommand().equals("Save Right")){
			save(right);
		}
		else if(event.getActionCommand().equals("Save Left As")){
			saveAs(left);
		}
		else if(event.getActionCommand().equals("Save Right As")){
			saveAs(right);
		}
		else if(event.getActionCommand().equals("Exit")){
			if(!left.isDirty() && !right.isDirty()){
				System.exit(0);
			}
			exit = new ExitDialog();
			exit.setState(left, right);
			exit.setVisible(true);
		}
		else if(event.getActionCommand().equals("SaveFromExit")){

			if(exit.getLeftCheck()){
				save(left);
			}
			if(exit.getRightCheck()){
				save(right);
			}
			System.exit(0);
		}
		else if(event.getActionCommand().equals("SaveNoneFromExit")){
			System.exit(0);
		}
		else if(event.getActionCommand().equals("CancelFromExit")){
			exit.setVisible(false);
		}
		else if(event.getActionCommand().equals("Delete")){
			removeSelectedData();
		}
		else if(event.getActionCommand().equals("NewFolder")){
			handleNewFolder();
		}
		else if(event.getActionCommand().equals("DeleteFromFolder")){
			handleDeleteFromFolder();
		}
		((Component)event.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	} 

	private void handleGoToLineNumber() {
		int lineNum = goToDiag.getLineNumber();
		Table src = left.isFocused()?left.getTable():right.getTable();
		src.getSelectionModel().setSelectionInterval(lineNum - 1, lineNum - 1);	
		goToDiag.setVisible(false);
	}

	public static boolean deleteDir(File dir){
		if (dir.isDirectory()){
			String[] children = dir.list();
			for (int i=0; i<children.length; i++){ 
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) { 
					return false; 
				}
			}
		} 
		return dir.delete();  
	}
	private void handleDeleteFromFolder() {
		String filePath = getSelectedFilePath();
		File f = new File(filePath);
		if(f.exists()){
			if(f.isDirectory()) 
				deleteDir(f);
			else
				f.delete();
			System.out.println("File deleted:"+f.getAbsolutePath());
		}
	}

	/**
	 * Method that is called when we navigate in tabs. Used to update the context
	 * of tool bar and menu items
	 */
	@Override
	public void stateChanged(ChangeEvent event) {
		TabPane tabPane = (TabPane)event.getSource();
		this.parent = tabPane;
		if(tabPane.getSelectedComponent() instanceof AppSplitPane){
			this.pane = (AppSplitPane)tabPane.getSelectedComponent();  
			this.left = ((AppSplitPane)pane).getLeftRes();
			this.right = ((AppSplitPane)pane).getRightRes();
			ToolBar.getInstance().setSelectedFilter(((AppSplitPane)pane).getSelectedFilter());
			setMenuBar(new MenuBar());
			setToolBar(ToolBar.getInstance());
		}
		if(tabPane.getSelectedComponent() instanceof JCompareWindow){
			setMenuBar(new FolderMenuBar());
			setToolBar(FolderToolBar.getInstance());
			this.pane = tabPane.getSelectedComponent();
			setTrees(((JCompareWindow)pane).getLeftTree(), ((JCompareWindow)pane).getRightTree());
		}

	}

	private void setMenuBar(JMenuBar bar){
		if(mainWin == null){
			mainWin = getMainWindow();
		}
		if(mainWin != null){
			mainWin.setJMenuBar(bar);
		}
	}

	private void setToolBar(JToolBar bar){
		if(mainWin == null){
			mainWin = getMainWindow();
		}
		if(mainWin != null){
			mainWin.add(bar, BorderLayout.NORTH);
			SwingUtilities.updateComponentTreeUI(bar);
		}
	}

	private JFrame getMainWindow(){
		Component c = SwingUtilities.getRootPane(parent);
		if(c != null){
			return (JFrame)c.getParent();
		}
		return null;
	}
	/**
	 * Method that handles same filter button
	 */
	private void handleSameFilterButton(){
		DataFilter lf = new DataFilter(left);
		DataFilter rf = new DataFilter(right);
		lf.setOnlySameFilter(left.getTable(), left.getModel());
		rf.setOnlySameFilter(right.getTable(), right.getModel());
	}

	/**
	 * Method that handles different filter button
	 */
	private void handleDiffFilterButton(){
		DataFilter lf = new DataFilter(left);
		DataFilter rf = new DataFilter(right);
		lf.setOnlyDifferentFilter(left.getTable(), left.getModel());
		rf.setOnlyDifferentFilter(right.getTable(), right.getModel());
	}

	/**
	 * Method that handles all filter button
	 */
	private void handleAllFilterButton(){
		left.getTable().setRowSorter(null);
		right.getTable().setRowSorter(null);
	}

	/**
	 * Copy rows from left to right
	 */
	private void handleCopyToRight(){
		copyRows(left.getTable(), right.getTable());
	}

	/**
	 * Copy rows from right to left
	 */
	private void handleCopyToLeft(){
		copyRows(right.getTable(), left.getTable());
	}

	/**
	 * Method to copy rows from one table to another
	 * @param src
	 * @param dst
	 */
	private void copyRows(Table src, Table dst){
		int rows[] = src.getSelectedRows();
		/* Do sanity check to return in case nothing is selected */
		if(rows.length == 0) return;
		TableModel dstModel = (TableModel)dst.getModel();
		TableModel srcModel = (TableModel)src.getModel();
		ArrayList<DataRow> dstData = dstModel.getLoader().getData();
		ArrayList<DataRow> srcData = srcModel.getLoader().getData();
		for(int i : rows){
			/* Register event with the undo fw */
			ReplaceRow rRow = new ReplaceRow(src, dst,  i);
			((AppSplitPane)pane).getUndoSupport().postEdit(rRow);
			/* Replace the target rows with our data */
			dstData.remove(i);
			dstData.add(i, srcData.get(i));
		}
		/* Fire event for the rows for which data changed */
		//dst.tableChanged(new TableModelEvent(dst.getModel(), rows[0], rows[rows.length - 1]));
		dst.tableChanged(new TableModelEvent(dst.getModel()));
	}

	/**
	 * Center the selected cell to center of the screen
	 * @param table
	 * @param x
	 * @param y
	 */
	private void centerCell(Table table, int x, int y) {
		Rectangle visible = table.getVisibleRect();
		Rectangle cell = table.getCellRect(x, y, true);
		if((cell != null) && (visible != null)){
			cell.grow((visible.width - cell.width) / 2, (visible.height - cell.height) / 2);
			table.scrollRectToVisible(cell);
		}
	}

	/**
	 * Shift the focus on next difference
	 */
	private void handleGetNextDiff(){
		Table leftTable = left.getTable();
		Table rightTable = right.getTable();
		TableModel leftModel = (TableModel)left.getModel();
		TableModel rightModel = (TableModel)right.getModel();
		ArrayList<DataRow> lData = leftModel.getLoader().getData();
		ArrayList<DataRow> rData = rightModel.getLoader().getData();
		int selected = Math.min(leftTable.getSelectedRow(), rightTable.getSelectedRow());
		if((lData != null) && (rData != null)){
			for(int i = selected + 1; (i < lData.size()) && (i < rData.size()); i++){
				if((lData.get(i).getData() == null)||(rData.get(i).getData() == null)|| !lData.get(i).getData().equals(rData.get(i).getData())){
					leftTable.changeSelection(i,0, false, false);
					centerCell(leftTable, i, 0);
					leftTable.requestFocusInWindow();
					rightTable.changeSelection(i,0, false, false);
					centerCell(rightTable, i, 0);
					rightTable.requestFocusInWindow();
					break;
				}
			}
		}
	}

	/**
	 * Shift focus to previous difference 
	 */
	private void handleGetPrevDiff(){
		Table leftTable = left.getTable();
		Table rightTable = right.getTable();
		TableModel leftModel = (TableModel)left.getModel();
		TableModel rightModel = (TableModel)right.getModel();
		ArrayList<DataRow> lData = leftModel.getLoader().getData();
		ArrayList<DataRow> rData = rightModel.getLoader().getData();
		int selected = Math.min(leftTable.getSelectedRow(), rightTable.getSelectedRow());
		System.out.println(selected);
		if((lData != null) && (rData != null)){
			for(int i = selected - 1; (i > 0) && (i > 0); i--){
				if((lData.get(i).getData() == null)||(rData.get(i).getData() == null)|| !lData.get(i).getData().equals(rData.get(i).getData())){
					leftTable.changeSelection(i,0, false, false);
					centerCell(leftTable, i, 0);
					leftTable.requestFocusInWindow();
					rightTable.changeSelection(i,0, false, false);
					centerCell(rightTable, i, 0);
					rightTable.requestFocusInWindow();
					break;
				}
			}
		}
	}

	/**
	 * Handle the action when we are looking for some string in the file
	 */
	private void handleFindString(){
		boolean isBoth = findData.isFindInBoth();
		if(isBoth){
			int fromLeftRow = left.getTable().getSelectedRow();
			int foundLeftRow = (fromLeftRow >= 0)?fromLeftRow:0;
			if(findData.isRegex()){
				foundLeftRow = findDataUsingRegex(fromLeftRow, left.getTable(), findData.getSearchString(), findData.isSearchUp());
			}
			else{
				foundLeftRow = findDataUsingWord(fromLeftRow, left.getTable(), findData.getSearchString(), findData.isSearchUp());
			}
			int fromRightRow = right.getTable().getSelectedRow();
			int foundRightRow = (fromRightRow >= 0)? fromRightRow: 0;
			if(findData.isRegex()){
				foundRightRow = findDataUsingRegex(fromRightRow, right.getTable(), findData.getSearchString(), findData.isSearchUp());
			}
			else{
				foundRightRow = findDataUsingWord(fromRightRow, right.getTable(), findData.getSearchString(), findData.isSearchUp());
			}
			/* We found only in right */
			if(fromLeftRow == foundLeftRow && foundRightRow != fromRightRow){
				right.getTable().changeSelection(foundRightRow,0, false, false);
				centerCell(right.getTable(), foundRightRow, 0);
				right.getTable().requestFocusInWindow();
			}
			/* We find only in left */
			else if(fromLeftRow != foundLeftRow && foundRightRow == fromRightRow){
				left.getTable().changeSelection(foundLeftRow,0, false, false);
				centerCell(left.getTable(), foundLeftRow, 0);
				left.getTable().requestFocusInWindow();
			}
			else if(foundRightRow < foundLeftRow){
				right.getTable().changeSelection(foundRightRow,0, false, false);
				centerCell(right.getTable(), foundRightRow, 0);
				right.getTable().requestFocusInWindow();
			}
			else{
				left.getTable().changeSelection(foundLeftRow,0, false, false);
				centerCell(left.getTable(), foundLeftRow, 0);
				left.getTable().requestFocusInWindow();
			}
		}
		else{
			Table fromTable = (findData.isFindInLeft())?left.getTable():right.getTable();
			int fromRow = (findData.isFindInLeft())?left.getTable().getSelectedRow():right.getTable().getSelectedRow();
			int foundRow = fromRow;
			if(findData.isRegex()){
				foundRow = findDataUsingRegex(fromRow, fromTable, findData.getSearchString(), findData.isSearchUp());
			}
			else{
				foundRow = findDataUsingWord(fromRow, fromTable, findData.getSearchString(), findData.isSearchUp());
			}
			fromTable.changeSelection(foundRow,0, false, false);
			centerCell(fromTable, foundRow, 0);
			fromTable.requestFocusInWindow();
		}
	}

	/**
	 * Find in the table using the whole word
	 * @param fromRow
	 * @param table
	 * @param data
	 * @param isBackward
	 * @return
	 */
	private int findDataUsingWord(int fromRow, Table table, String data, boolean isBackward){
		ArrayList<DataRow> list = ((TableModel)table.getModel()).getLoader().getData();
		if(isBackward){
			for(int i = fromRow - 1; i > 0; i--){
				if(findData.isCaseInSensitive()){
					if(findData.isMatchWholeWord()){
						if(list.get(i).getData()!= null && list.get(i).getData().equalsIgnoreCase(data)) return i;
					}
					else{
						if(list.get(i).getData()!= null && list.get(i).getData().contains(data)) return i;
					}

				}
				else{
					if(findData.isMatchWholeWord()){
						if(list.get(i).getData()!= null && list.get(i).getData().equals(data)) return i;
					}
					else{
						if(list.get(i).getData()!= null && list.get(i).getData().contains(data)) return i;
					}
				}
			}
		}
		else{
			for(int i = fromRow + 1; i < list.size(); i++){
				if(findData.isCaseInSensitive()){
					if(findData.isMatchWholeWord()){
						if(list.get(i).getData()!= null && list.get(i).getData().equalsIgnoreCase(data)) return i;
					}
					else{
						if(list.get(i).getData()!= null && list.get(i).getData().contains(data)) return i;
					}

				}
				else{
					if(findData.isMatchWholeWord()){
						if(list.get(i).getData()!= null && list.get(i).getData().equals(data)) return i;
					}
					else{
						if(list.get(i).getData()!= null && list.get(i).getData().contains(data)) return i;
					}

				}
			}
		}
		return fromRow;
	}

	/**
	 * Find data using regular expressions
	 * @param fromRow
	 * @param table
	 * @param data
	 * @param isBackward
	 * @return
	 */
	private int findDataUsingRegex(int fromRow, Table table, String data, boolean isBackward){
		ArrayList<DataRow> list = ((TableModel)table.getModel()).getLoader().getData();
		Pattern p = null;
		/* If  '*' is at beginning insert the '^' else it will give error */
		if(data.indexOf('*') == 0){
			StringBuffer buff = new StringBuffer("^");
			buff.append(data);
			data = buff.toString();
		}
		if(findData.isCaseInSensitive()){
			p = Pattern.compile(data, Pattern.CASE_INSENSITIVE);
		}
		else{
			p = Pattern.compile(data);
		}
		if(!isBackward){
			for(int i = fromRow + 1; i < list.size(); i++){
				if(list.get(i).getData() != null){
					Matcher m = p.matcher(list.get(i).getData());
					if(m.find()) return i;
					m.reset();
				}
			}
		}
		else{
			for(int i = fromRow - 1; i > 0; i--){
				if(list.get(i).getData() != null){
					Matcher m = p.matcher(list.get(i).getData());
					if(m.find()) return i;
					m.reset();
				}
			}	
		}
		return fromRow;
	}

	/**
	 * Handle reload event 
	 */
	private void handleReload(){
		AppStatusBar.runTaskWithProgressBar();
		try{
			AppSplitPane p = new AppSplitPane(left.getFileName(), right.getFileName());
			parent.add(p,parent.getSelectedIndex());
			parent.remove(parent.getSelectedIndex());
			parent.setSelectedComponent(p);
			//			left.setLoader(new DataLoader(left.getFileName()));
			//			left.getLoader().getDataFromFile();
			//			right.setLoader(new DataLoader(right.getFileName()));
			//			right.getLoader().getDataFromFile();
			//			Algorithms.matchArrayData(left.getLoader().getData(), right.getLoader().getData());
			//			left.getTable().setRowSelectionInterval(0, 0);
			//			right.getTable().setRowSelectionInterval(0, 0);
			left.getTable().tableChanged(new TableModelEvent(left.getModel()));
			right.getTable().tableChanged(new TableModelEvent(right.getModel()));
		}catch(Exception e){
			e.printStackTrace();
		}
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	/**
	 * Utility function to swap objects
	 * @param first
	 * @param second
	 */
	@SuppressWarnings("unused")
	private void swapObjects(Object first, Object second){
		Object temp = first;
		first = second;
		second = temp;
	}
	/**
	 * Swap panes, we need to swap the resources too else it will be wrong data,
	 * while copying from one file to other. 
	 */
	private void handleSwapPanes(){
		Component leftPane = ((AppSplitPane)pane).getLeftComponent();
		Component rightPane = ((AppSplitPane)pane).getRightComponent();
		((AppSplitPane)pane).removeAll();
		((AppSplitPane)pane).updateUI();
		((AppSplitPane)pane).setLeftComponent(rightPane);
		((AppSplitPane)pane).setRightComponent(leftPane);
		((AppSplitPane)pane).updateUI();
	}

	/**
	 * Handle event to show line numbers
	 * @param value
	 */
	private void handleShowLineNumbers(boolean value){
		left.getModel().setShowLineNumbers(value);
		right.getModel().setShowLineNumbers(value);
	}

	/**
	 * Handle event to show white spaces
	 * @param value
	 */
	private void handleShowWhiteSpaces(boolean value){
		left.getModel().setShowWhiteSpacesEnable(value);
		right.getModel().setShowWhiteSpacesEnable(value);
	}

	/**
	 * Add new tab to the pane
	 */
	private void handleNewTab(){
		parent.add(new AppSplitPane(null, null));
		parent.setTabComponentAt(parent.getTabCount() - 1, new TabComponent(parent,Config.getProperty("NEW.DOCUMENT")));
		parent.setSelectedIndex(parent.getTabCount() - 1);
	}

	private void handleNewFolder(){
		parent.add(new JCompareWindow());
		parent.setTabComponentAt(parent.getTabCount() - 1, new TabComponent(parent,Config.getProperty("NEW.DOCUMENT")));
		parent.setSelectedIndex(parent.getTabCount() - 1);
	}

	/**
	 * Add new tab to the pane
	 */
	public void handleOpenFiles(String fileLeft, String fileRight){
		AppSplitPane sp = new AppSplitPane(fileLeft, fileRight); 
		parent.add(sp);
		parent.setTabComponentAt(parent.getTabCount() - 1, new TabComponent(parent,sp.getName()));
		parent.setSelectedIndex(parent.getTabCount() - 1);
	}
	/**
	 * selects the whole document to perform an action
	 */
	private void handleSelectAll(){
		Table table = left.isFocused()?left.getTable():right.getTable();
		table.getSelectionModel().setSelectionInterval(0, table.getRowCount());		
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {

	}

	public void setDataOnClipBoard(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		int rows[] = src.getSelectedRows();
		ArrayList<DataRow> list = new ArrayList<DataRow>();
		for(int i :rows){
			if(srcList.get(i) instanceof EmptyDataRow){
				//list.add(new StringDataRow("\r\n"));
			}
			else{
				list.add(srcList.get(i));
			}
		}
		clipboard.setContents(new ClipBoardData(list), null);
	}

	public void getDataFromClipBoard(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String result = null;
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && 	contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if ( hasTransferableText ) {
			try {
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (UnsupportedFlavorException ex){
				ex.printStackTrace();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}

			Table src = left.isFocused()?left.getTable():right.getTable();
			TableModel model = (TableModel)src.getModel();
			List<DataRow> srcList = model.getLoader().getData();
			StringBuffer sbuff = new StringBuffer();
			int j = src.getSelectedRow();
			int origIndex = j;
			for(int i = 0; i < result.length(); i++){
				char c = result.charAt(i);
				if(c == '\n'){
					sbuff.append(c);
					srcList.add(j, new StringDataRow(sbuff.toString()));
					sbuff.setLength(0);
					j++;
				}
				else{
					sbuff.append(c);
				}
			}
			/* Register event with the undo fw */ 
			CopyPasteRows paste = new CopyPasteRows(this, left, result, srcList, origIndex);
			((AppSplitPane)pane).getUndoSupport().postEdit(paste);
			refresh();
			//src.tableChanged(new TableModelEvent(src.getModel(), origIndex, i));
			left.getTable().tableChanged(new TableModelEvent(left.getModel()));
			right.getTable().tableChanged(new TableModelEvent(right.getModel()));
		}
	}

	private void removeSelectedData(){
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		int rows[] = src.getSelectedRows();
		CutPasteRows cut = new CutPasteRows(this, left, srcList, rows[0], rows[rows.length - 1]);
		for(int i = rows[0]; i <= rows[rows.length - 1]; i++){
			srcList.remove(rows[0]);
		}
		/* Register event with the undo fw */
		((AppSplitPane)pane).getUndoSupport().postEdit(cut);
		refresh();
		left.getTable().tableChanged(new TableModelEvent(src.getModel()));
		right.getTable().tableChanged(new TableModelEvent(src.getModel()));
	}

	public void refresh(){
		AppStatusBar.runTaskWithProgressBar();
		try{
			Algorithms.matchArrayData(left.getLoader().getData(), right.getLoader().getData());
		}catch(Exception e){
			e.printStackTrace();
		}
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	private void handleTrimTrailingWhiteSpaces(){
		AppStatusBar.runTaskWithProgressBar();
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		for(int i = 0; i < srcList.size(); i++){
			DataRow row = srcList.get(i);
			row =  new StringDataRow(row.getData().replaceAll("\\s+$", ""));
			srcList.set(i, row);
		}
		src.tableChanged(new TableModelEvent(src.getModel()));
		AppStatusBar.notifyEndTaskWithProgressBar();
	}
	private void handleLeadingSpaceToTabs(){
		AppStatusBar.runTaskWithProgressBar();
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		for(int i = 0; i < srcList.size(); i++){
			String row = srcList.get(i).getData();
			StringBuffer sb = new StringBuffer();
			for(int j = 0; j < row.length(); j++){
				if(row.charAt(j) != ' '){
					sb.append(row.substring(j));
					break;
				}
				sb.append("\t");
			}
			srcList.set(i, new StringDataRow(sb.toString()));
		}
		src.tableChanged(new TableModelEvent(src.getModel()));
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	private void handleTabsToSpaces(){
		AppStatusBar.runTaskWithProgressBar();
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		for(int i = 0; i < srcList.size(); i++){
			String row = srcList.get(i).getData();
			StringBuffer sb = new StringBuffer();
			for(int j = 0; j < row.length(); j++){
				if(row.charAt(j) == '\t'){
					sb.append("    ");
				}
				else{
					sb.append(row.charAt(j));
				}
			}
			srcList.set(i, new StringDataRow(sb.toString()));
		}
		src.tableChanged(new TableModelEvent(src.getModel()));
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	private void handleConvertWindowsToUnix(){
		AppStatusBar.runTaskWithProgressBar();
		Table src = left.isFocused()?left.getTable():right.getTable();
		TableModel model = (TableModel)src.getModel();
		List<DataRow> srcList = model.getLoader().getData();
		for(int i = 0; i < srcList.size(); i++){
			String row = srcList.get(i).getData();
			if(row != null){
				System.out.println(row.indexOf("\r"));
				row = row.replaceAll("\r", "");
				srcList.set(i, new StringDataRow(row));
			}
		}
		src.tableChanged(new TableModelEvent(src.getModel()));
		AppStatusBar.notifyEndTaskWithProgressBar();
	}

	private void save(){
		Resources res = left.isFocused()?left:right;
		res.getLoader().save();
	}

	private void save(Resources res){
		res.getLoader().save();
	}

	private void saveAs(){
		try{
			Resources res = left.isFocused()?left:right;
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showSaveDialog(pane);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				res.getLoader().saveAs(file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void saveAs(Resources res){
		try{
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showSaveDialog(pane);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				res.getLoader().saveAs(file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}



}
