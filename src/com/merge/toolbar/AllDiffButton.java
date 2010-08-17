package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class AllDiffButton extends ToolBarToggleButton{

	private static final long serialVersionUID = 1L;

	public AllDiffButton() {
		super("TOOLBAR.ALL.DIFF.BUTTON");
		setToolTipText("Show all differences");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("AllFilter");
	}
}
