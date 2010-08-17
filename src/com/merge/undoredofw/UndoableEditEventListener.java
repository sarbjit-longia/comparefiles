package com.merge.undoredofw;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

public class UndoableEditEventListener implements UndoableEditListener{

	UndoManager manager = null;
	public UndoableEditEventListener(UndoManager manager) {
		this.manager =  manager;
	}
	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		UndoableEdit edit = e.getEdit();
     	manager.addEdit( edit );
	}
}
