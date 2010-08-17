package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class ShowWhiteSpacesButton extends ToolBarToggleButton{

	private static final long serialVersionUID = 1L;
	public ShowWhiteSpacesButton() {
		super("TOOLBAR.SHOW.WS.BUTTON");
		setToolTipText("Show whitespaces");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("ShowWhiteSpaces");
	}
}
