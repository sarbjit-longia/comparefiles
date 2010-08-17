package com.merge.folder.menubar;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

/**
 * This class implements the view menu
 * @author singhs
 *
 */
public class ViewMenu extends Menu{

	private static final long serialVersionUID = 840820489842863090L;
	
	MenuItem menuShowAll = null;
	MenuItem menuShowDiff = null;
	MenuItem menuShowSame = null;
	
	public ViewMenu() {
		super(Config.getProperty("MENU.VIEW"));
		menuShowAll = new MenuItem("MENU.VIEW.SHOW.ALL", null, null);
		menuShowDiff = new MenuItem("MENU.VIEW.DIFF", null, null);
		menuShowSame = new MenuItem("MENU.VIEW.SAME", null, null);
		add(menuShowAll);
		add(menuShowDiff);
		add(menuShowSame);
		
		menuShowAll.addActionListener(ActionEventListeners.getInstance());
		menuShowAll.setActionCommand("AllFilter");
		menuShowDiff.addActionListener(ActionEventListeners.getInstance());
		menuShowDiff.setActionCommand("DiffFilter");
		menuShowSame.addActionListener(ActionEventListeners.getInstance());
		menuShowSame.setActionCommand("SameFilter");
	}
}
