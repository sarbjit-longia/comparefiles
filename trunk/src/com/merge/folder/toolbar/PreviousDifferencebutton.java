package com.merge.folder.toolbar;

import com.merge.listeners.ActionEventListeners;

public class PreviousDifferencebutton extends ToolBarButton{

	private static final long serialVersionUID = -3332204656271817155L;

	public PreviousDifferencebutton() {
		super("TOOLBAR.PREV.DIFF.BUTTON");
		setToolTipText("Previous Difference");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("PrevDiff");
	}
}