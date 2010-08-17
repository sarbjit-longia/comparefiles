package com.merge.menubar;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

/**
 * This class implements the search menu
 * @author singhs
 *
 */
public class SearchMenu extends Menu{
	private static final long serialVersionUID = -3429829638185745554L;

	MenuItem menuNext = null;
	MenuItem menuPrev = null;
	MenuItem menuFind = null;
	MenuItem menuFindNext = null;
	MenuItem menuFindPrev = null;
	MenuItem menuGoTo = null;
	
	public SearchMenu() {
		super(Config.getProperty("MENU.SEARCH"));
		menuNext = new MenuItem("MENU.SEARCH.NEXT", "MENU.SEARCH.NEXT.ICON","MENU.SEARCH.NEXT.ACC");
		menuPrev = new MenuItem("MENU.SEARCH.PREVIOUS", "MENU.SEARCH.PREVIOUS.ICON","MENU.SEARCH.PREVIOUS.ACC");
		menuFind = new MenuItem("MENU.SEARCH.FIND", "MENU.SEARCH.FIND.ICON","MENU.SEARCH.FIND.ACC");
		menuFindNext = new MenuItem("MENU.SEARCH.FIND.NEXT", null,"MENU.SEARCH.FIND.NEXT.ACC");
		menuFindPrev = new MenuItem("MENU.SEARCH.FIND.PREVIOUS", null,"MENU.SEARCH.FIND.PREVIOUS.ACC");
		menuGoTo = new MenuItem("MENU.SEARCH.GOTO", null,"MENU.SEARCH.GOTO.ACC");
		add(menuNext);
		add(menuPrev);
		addSeparator();
		add(menuFind);
		add(menuFindNext);
		add(menuFindPrev);
		add(menuGoTo);
		menuNext.addActionListener(ActionEventListeners.getInstance());
		menuNext.setActionCommand("NextDiff");
		menuPrev.addActionListener(ActionEventListeners.getInstance());
		menuPrev.setActionCommand("PrevDiff");
		menuFind.addActionListener(ActionEventListeners.getInstance());
		menuFind.setActionCommand("Lookup");
		menuFindNext.addActionListener(ActionEventListeners.getInstance());
		menuFindNext.setActionCommand("FindNext");
		menuFindPrev.addActionListener(ActionEventListeners.getInstance());
		menuFindPrev.setActionCommand("FindPrev");
		menuGoTo.addActionListener(ActionEventListeners.getInstance());
		menuGoTo.setActionCommand("GoTo");
	}
}
