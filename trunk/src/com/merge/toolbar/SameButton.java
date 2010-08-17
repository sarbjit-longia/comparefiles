package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class SameButton extends ToolBarToggleButton{

	private static final long serialVersionUID = 1L;
	public SameButton() {
		super("TOOLBAR.SAME.BUTTON");
		setToolTipText("Show only similarities");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("SameFilter");
	}

}
