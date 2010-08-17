package com.merge.menubar;

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
	CheckBoxMenuItem menuShowWS = null;
	CheckBoxMenuItem menuShowLineNumber = null;
	MenuItem menuIgnUnimpDiff = null;
	
	public ViewMenu() {
		super(Config.getProperty("MENU.VIEW"));
		menuShowAll = new MenuItem("MENU.VIEW.SHOW.ALL", null, null);
		menuShowDiff = new MenuItem("MENU.VIEW.DIFF", null, null);
		menuShowSame = new MenuItem("MENU.VIEW.SAME", null, null);
		menuShowWS = new CheckBoxMenuItem("MENU.VIEW.VISIBLE.WS", null, null);
		menuShowLineNumber = new CheckBoxMenuItem("MENU.VIEW.SHOW.LN", null, null);
		menuIgnUnimpDiff = new MenuItem("MENU.VIEW.IGN.UNIMP", null, null);
		
		add(menuShowAll);
		add(menuShowDiff);
		add(menuShowSame);
		addSeparator();
		add(menuIgnUnimpDiff);
		addSeparator();
		add(menuShowWS);
		add(menuShowLineNumber);
		
		menuShowAll.addActionListener(ActionEventListeners.getInstance());
		menuShowAll.setActionCommand("AllFilter");
		menuShowDiff.addActionListener(ActionEventListeners.getInstance());
		menuShowDiff.setActionCommand("DiffFilter");
		menuShowSame.addActionListener(ActionEventListeners.getInstance());
		menuShowSame.setActionCommand("SameFilter");
		menuShowWS.addActionListener(ActionEventListeners.getInstance());
		menuShowWS.setActionCommand("ShowWhiteSpaces");
		menuShowLineNumber.addActionListener(ActionEventListeners.getInstance());
		menuShowLineNumber.setActionCommand("ShowLineNumbers");
		
	}
}
