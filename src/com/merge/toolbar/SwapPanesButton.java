package com.merge.toolbar;

import com.merge.listeners.ActionEventListeners;

public class SwapPanesButton extends ToolBarButton{

	private static final long serialVersionUID = 1L;
	public SwapPanesButton() {
		super("TOOLBAR.SWAP.PANES.BUTTON");
		setToolTipText("Swap Panes");
		addActionListener(ActionEventListeners.getInstance());
		setActionCommand("SwapPanes");
	}
}
