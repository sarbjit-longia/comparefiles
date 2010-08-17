package com.merge.folder.toolbar;

import com.merge.listeners.ActionEventListeners;

public class NotSameButton extends ToolBarToggleButton{

	private static final long serialVersionUID = 1L;
	public NotSameButton() {
		super("TOOLBAR.NOT.SAME.BUTTON");
		setToolTipText("Show only disimilarities");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("DiffFilter");
	}
}
