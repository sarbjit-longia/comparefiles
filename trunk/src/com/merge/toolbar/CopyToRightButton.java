package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class CopyToRightButton extends ToolBarButton{

	private static final long serialVersionUID = 1L;

	public CopyToRightButton() {
		super("TOOLBAR.COPYTORIGHT.BUTTON");
		setToolTipText("Copy from left to right pane");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("CopyToRight");
	}
}
