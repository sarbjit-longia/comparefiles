package com.merge.undoredofw;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import com.merge.listeners.ActionEventListeners;
import com.merge.splitpane.DataRow;
import com.merge.splitpane.Resources;

public class CutPasteRows extends AbstractUndoableEdit{

	private static final long serialVersionUID = 1L;
	private ArrayList<DataRow> rowData = null;
	private int from, to;
	private List<DataRow> model = null;
	private ActionEventListeners listener = null;
	private Resources res = null;
	public CutPasteRows(ActionEventListeners listener, Resources res, List<DataRow> model, int from, int to){
		this.model = model;
		this.from = from;
		this.res = res;
		this.listener = listener;
		this.to = to;
		rowData = new ArrayList<DataRow>();
		for(int i = from ; i <= to; i++){
			rowData.add(model.get(i));
		}
	}

	public void undo() throws CannotUndoException {
		for(DataRow r : rowData){
			model.add(from, r);
		}
		listener.refresh();
		res.getTable().tableChanged(new TableModelEvent(res.getTable().getModel()));
		res.getOther().getTable().tableChanged(new TableModelEvent(res.getOther().getTable().getModel()));
	}
	
	public void redo() throws CannotUndoException {
		for(int i = 0; i < rowData.size(); i++){
			model.remove(from);
		}
		listener.refresh();
		res.getTable().tableChanged(new TableModelEvent(res.getTable().getModel()));
		res.getOther().getTable().tableChanged(new TableModelEvent(res.getOther().getTable().getModel()));
	}

	public boolean canRedo(){return true;}
	public boolean canUndo(){return true;}
}
