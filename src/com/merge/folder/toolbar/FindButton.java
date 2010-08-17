package com.merge.folder.toolbar;

import com.merge.listeners.ActionEventListeners;

public class FindButton extends ToolBarButton{

	private static final long serialVersionUID = 6616157984601025240L;

	public FindButton() {
		super("TOOLBAR.FIND.BUTTON");
		setToolTipText("Lookup");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("Lookup");
	}
}