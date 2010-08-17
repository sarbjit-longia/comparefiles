package com.merge.folder.toolbar;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

public class FolderToolBar extends JToolBar {
	
	private static final long serialVersionUID = 1L;
	ButtonGroup group = new ButtonGroup();
	AllDiffButton allDiff = new AllDiffButton();
	SameButton allSame = new SameButton();
	NotSameButton notSame = new NotSameButton();
	static FolderToolBar instance = null;
		
	private FolderToolBar() {
		super();
		add(allDiff);
		group.add(allDiff);
		add(allSame);
		group.add(allSame);
		group.add(notSame);
		add(notSame);
		group.setSelected(allDiff.getModel(), true);
		addSeparator();
		add(new CopyToLeftButton());
		add(new CopyToRightButton());
		addSeparator();
		add(new NextDifferenceButton());
		add(new PreviousDifferencebutton());
		add(new FindButton());
		addSeparator();
		add(new ReloadButton());
		/* See if we want to get the color from config file */
		String clr = Config.getProperty("TOOLBAR.BG.COLOR");
		if (clr != null){
			String c[] = clr.split(","); 
			setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
		}
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setBorderPainted(true);
		setMargin(new Insets(3, 0, 2, 0));
	}
	
	public static FolderToolBar getInstance(){
		if(instance == null){
			instance = new FolderToolBar();
		}
		return instance;
	}
	
	public void setSelectedFilter(int filter){
		switch(filter){
		case ActionEventListeners.ALL_FILTER:
			group.setSelected(allDiff.getModel(), true);
			break;
		case ActionEventListeners.DIFF_FILTER:
			group.setSelected(notSame.getModel(), true);
			break;
		case ActionEventListeners.SAME_FILTER:
			group.setSelected(allSame.getModel(), true);
			break;
		default:
				assert(true);
		}
	}
}
