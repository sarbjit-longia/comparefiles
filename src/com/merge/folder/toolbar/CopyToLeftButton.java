package com.merge.folder.toolbar;

import com.merge.listeners.ActionEventListeners;

public class CopyToLeftButton extends ToolBarButton{

	private static final long serialVersionUID = 1L;

	public CopyToLeftButton() {
		super("TOOLBAR.COPYTOLEFT.BUTTON");
		setToolTipText("Copy from right to left pane");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("CopyToLeft");
	}

}
