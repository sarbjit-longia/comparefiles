package com.merge.menubar;

import java.awt.event.KeyEvent;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

public class FileMenu extends Menu {

	private static final long serialVersionUID = 5374137776597081893L;
	
	MenuItem menuNew = null;
	MenuItem menuSave = null;
	MenuItem menuSaveAs = null;
	MenuItem menuSaveLeft = null;
	MenuItem menuSaveLeftAs = null;
	MenuItem menuSaveRight = null;
	MenuItem menuSaveRightAs = null;
	MenuItem menuOpen = null;
	MenuItem menuExit = null;
	public FileMenu() {
		super(Config.getProperty("MENU.FILE"));
		menuNew = new MenuItem("MENU.FILE.NEW", "MENU.FILE.NEW.ICON", "MENU.FILE.NEW.ACC", KeyEvent.VK_N);
		menuSave = new MenuItem("MENU.FILE.SAVE", "MENU.FILE.SAVE.ICON", "MENU.FILE.SAVE.ACC", KeyEvent.VK_S);
		menuSaveLeft = new MenuItem("MENU.FILE.SAVE", null, null);
		menuSaveRight = new MenuItem("MENU.FILE.SAVE", null, null);
		menuSaveLeftAs = new MenuItem("MENU.FILE.SAVE.AS.LEFT", null, null);
		menuSaveRightAs = new MenuItem("MENU.FILE.SAVE.AS.RIGHT", null, null);
		menuSaveAs = new MenuItem("MENU.FILE.SAVEAS", null, "MENU.FILE.SAVEAS.ACC", KeyEvent.VK_F12);
		menuExit = new MenuItem("MENU.FILE.EXIT", null, null);
		add(menuNew);
		addSeparator();
		add(menuSave);
		add(menuSaveAs);
		SaveMenu saveLeft = new SaveMenu(Config.getProperty("MENU.FILE.SAVE.LEFT"));
		saveLeft.add(menuSaveLeft);
		saveLeft.add(menuSaveLeftAs);
		add(saveLeft);
		SaveMenu saveRight = new SaveMenu(Config.getProperty("MENU.FILE.SAVE.RIGHT"));
		saveRight.add(menuSaveRight);
		saveRight.add(menuSaveRightAs);
		add(saveRight);
		addSeparator();
		add(menuExit);
		menuNew.addActionListener(ActionEventListeners.getInstance());
		menuNew.setActionCommand("New");
		menuSave.addActionListener(ActionEventListeners.getInstance());
		menuSave.setActionCommand("Save");
		menuSaveAs.addActionListener(ActionEventListeners.getInstance());
		menuSaveAs.setActionCommand("SaveAs");
		menuSaveLeft.addActionListener(ActionEventListeners.getInstance());
		menuSaveLeft.setActionCommand("Save Left");
		menuSaveRight.addActionListener(ActionEventListeners.getInstance());
		menuSaveRight.setActionCommand("Save Right");
		menuSaveLeftAs.addActionListener(ActionEventListeners.getInstance());
		menuSaveLeftAs.setActionCommand("Save Left As");
		menuSaveRightAs.addActionListener(ActionEventListeners.getInstance());
		menuSaveRightAs.setActionCommand("Save Right As");
		menuExit.addActionListener(ActionEventListeners.getInstance());
		menuExit.setActionCommand("Exit");
	}
	
	class SaveMenu extends Menu{
		private static final long serialVersionUID = 1L;
		public SaveMenu(String name) {
			super(name);
		}
		public void addMenuItem(MenuItem item){
			add(item);
		}
	}
}
