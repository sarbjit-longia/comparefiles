package com.merge.splitpane;

import javax.swing.JTabbedPane;

import com.merge.listeners.ActionEventListeners;

public class TabPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	public TabPane(){
		super();
		addChangeListener(ActionEventListeners.getInstance());
		
	}
}
