package com.merge.folder.menubar;

import javax.swing.JMenuItem;

import com.merge.listeners.ActionEventListeners;

/**
 * Class that implements the Edit menu of application
 * @author singhs
 *
 */
public class EditMenu extends Menu{

	private static final long serialVersionUID = 53247291209905501L;
	JMenuItem menuCut = null;
	JMenuItem menuCopy = null;
	JMenuItem menuPaste = null;
	JMenuItem menuDelete = null;
	JMenuItem menuSelectAll = null;
	JMenuItem menuUndo = null;
	JMenuItem menuRedo = null;
	JMenuItem menuCopyTo = null;
	Menu convert = null;
	/**
	 * Constructor
	 */
	public EditMenu() {
		super("Edit");
		menuDelete = new MenuItem("MENU.EDIT.DELETE", "MENU.EDIT.DELETE.ICON", "MENU.EDIT.DELETE.ACC"); 
		add(menuDelete);
		menuDelete.addActionListener(ActionEventListeners.getInstance());
		menuDelete.setActionCommand("DeleteFromFolder");
	}
}
