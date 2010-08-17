package com.merge.folder.menubar;

import java.awt.event.KeyEvent;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

public class FileMenu extends Menu {

	private static final long serialVersionUID = 5374137776597081893L;
	
	MenuItem menuNew = null;
	MenuItem menuOpen = null;
	MenuItem menuExit = null;
	public FileMenu() {
		super(Config.getProperty("MENU.FILE"));
		menuNew = new MenuItem("MENU.FILE.NEW", "MENU.FILE.NEW.ICON", "MENU.FILE.NEW.ACC", KeyEvent.VK_N);
		menuExit = new MenuItem("MENU.FILE.EXIT", null, null);
		add(menuNew);
		add(menuExit);
		menuNew.addActionListener(ActionEventListeners.getInstance());
		menuNew.setActionCommand("NewFolder");
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
