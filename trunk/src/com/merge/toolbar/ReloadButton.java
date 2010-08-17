package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class ReloadButton extends ToolBarButton{

	private static final long serialVersionUID = 1L;
	public ReloadButton() {
		super("TOOLBAR.REFRESH.BUTTON");
		setToolTipText("Refresh");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("Reload");
	}
}
