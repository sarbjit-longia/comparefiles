package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class NextDifferenceButton extends ToolBarButton{

	private static final long serialVersionUID = 1L;
	public NextDifferenceButton() {
		super("TOOLBAR.NEXT.DIFF.BUTTON");
		setToolTipText("Next Difference");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("NextDiff");
	}
}
