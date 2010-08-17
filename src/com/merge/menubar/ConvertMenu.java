package com.merge.menubar;

import javax.swing.ButtonGroup;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

public class ConvertMenu extends Menu {

	private static final long serialVersionUID = 5374137776597081893L;
	MenuItem removeTrailingWS = null;
	MenuItem leadSpacesToTabs = null;
	MenuItem tabsToSpaces = null;
	MenuItem lineEndings = null;
	
	
	public ConvertMenu() {
		super(Config.getProperty("MENU.EDIT.CONVERT"));
		removeTrailingWS = new MenuItem("MENU.EDIT.CONVERT.REMOVE.WS", null, null);
		leadSpacesToTabs = new MenuItem("MENU.EDIT.CONVERT.SPACES.TO.TABS", null, null);
		tabsToSpaces = new MenuItem("MENU.EDIT.CONVERT.TAB.TO.SPACES", null, null);
		add(removeTrailingWS);
		add(leadSpacesToTabs);
		add(tabsToSpaces);
		removeTrailingWS.addActionListener(ActionEventListeners.getInstance());
		removeTrailingWS.setActionCommand("RemoveWS");
		leadSpacesToTabs.addActionListener(ActionEventListeners.getInstance());
		leadSpacesToTabs.setActionCommand("SpaceToTabs");
		tabsToSpaces.addActionListener(ActionEventListeners.getInstance());
		tabsToSpaces.setActionCommand("TabsToSpaces");
		add(new LineEndings());
	}
}

class LineEndings extends Menu{

	private static final long serialVersionUID = 1L;
	CheckBoxMenuItem windows = null;
	CheckBoxMenuItem unix = null;
	CheckBoxMenuItem mac = null;
	
	public LineEndings() {
		super(Config.getProperty("MENU.EDIT.CONVERT.LINE.ENDINGS"));
		windows = new CheckBoxMenuItem("MENU.EDIT.CONVERT.LINE.ENDINGS.WINDOWS", null, null);
		unix = new CheckBoxMenuItem("MENU.EDIT.CONVERT.LINE.ENDINGS.UNIX", null, null);
		mac = new CheckBoxMenuItem("MENU.EDIT.CONVERT.LINE.ENDINGS.MAC", null, null);
		ButtonGroup grp = new ButtonGroup();
		grp.add(windows);
		grp.add(unix);
		grp.add(mac);
		add(windows);
		add(unix);
		add(mac);
		windows.addActionListener(ActionEventListeners.getInstance());
		windows.setActionCommand("ToWindows");
		unix.addActionListener(ActionEventListeners.getInstance());
		unix.setActionCommand("ToUnix");
		mac.addActionListener(ActionEventListeners.getInstance());
		mac.setActionCommand("ToMac");
	}
}
