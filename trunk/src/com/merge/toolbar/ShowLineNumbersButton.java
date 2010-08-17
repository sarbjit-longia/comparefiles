package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class ShowLineNumbersButton extends ToolBarToggleButton{

	private static final long serialVersionUID = 1L;
	public ShowLineNumbersButton() {
		super("TOOLBAR.SHOW.LN.BUTTON");
		setToolTipText("Show Line Numbers");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("ShowLineNumbers");
	}

}
