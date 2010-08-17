package com.merge.menubar;

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
		menuCut = new MenuItem("MENU.EDIT.CUT","MENU.EDIT.CUT.ICON","MENU.EDIT.CUT.ACC");
		menuCopy = new MenuItem("MENU.EDIT.COPY","MENU.EDIT.COPY.ICON","MENU.EDIT.COPY.ACC");
		menuPaste = new MenuItem("MENU.EDIT.PASTE", "MENU.EDIT.PASTE.ICON","MENU.EDIT.PASTE.ACC" );
		menuDelete = new MenuItem("MENU.EDIT.DELETE", "MENU.EDIT.DELETE.ICON", "MENU.EDIT.DELETE.ACC"); 
		menuSelectAll = new MenuItem("MENU.EDIT.SELECT.ALL", null,"MENU.EDIT.SELECT.ALL.ACC");
		menuUndo = new MenuItem("MENU.EDIT.UNDO", "MENU.EDIT.UNDO.ICON", "MENU.EDIT.UNDO.ACC");
		menuRedo = new MenuItem("MENU.EDIT.REDO", "MENU.EDIT.REDO.ICON", "MENU.EDIT.REDO.ACC");
		menuCopyTo = new MenuItem("MENU.EDIT.COPYTO", null, null);

		add(menuUndo);
		add(menuRedo);
		addSeparator();
		//add(menuCopyTo);
		//addSeparator();
		add(menuCopy);
		add(menuPaste);
		add(menuCut);
		add(menuDelete);
		addSeparator();
		add(menuSelectAll);
		add(new ConvertMenu());
		menuUndo.addActionListener(ActionEventListeners.getInstance());
		menuUndo.setActionCommand("Undo");
		menuRedo.addActionListener(ActionEventListeners.getInstance());
		menuRedo.setActionCommand("Redo");
		menuSelectAll.addActionListener(ActionEventListeners.getInstance());
		menuSelectAll.setActionCommand("SelectAll");
		menuCopy.addActionListener(ActionEventListeners.getInstance());
		menuCopy.setActionCommand("Copy");
		menuPaste.addActionListener(ActionEventListeners.getInstance());
		menuPaste.setActionCommand("Paste");
		menuCut.addActionListener(ActionEventListeners.getInstance());
		menuCut.setActionCommand("Cut");
		menuDelete.addActionListener(ActionEventListeners.getInstance());
		menuDelete.setActionCommand("Delete");
	}
}
