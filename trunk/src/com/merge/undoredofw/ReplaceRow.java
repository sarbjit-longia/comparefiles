package com.merge.undoredofw;

import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import com.merge.splitpane.DataRow;
import com.merge.splitpane.Table;
import com.merge.splitpane.TableModel;

public class ReplaceRow extends AbstractUndoableEdit{

	private static final long serialVersionUID = 1L;
	private DataRow rowData = null;
	private DataRow oldData = null;
	private int row;
	private ArrayList<DataRow> dstModel = null;
	Table table = null;
	Table other = null;
	
	public ReplaceRow(Table src, Table dst, int i) {
		this.rowData = ((TableModel)src.getModel()).getLoader().getData().get(i);
		this.row = i;
		this.oldData = ((TableModel)dst.getModel()).getLoader().getData().get(i);;
		this.table = dst;
		this.other = src;
		dstModel = ((TableModel)dst.getModel()).getLoader().getData();
	}

	public void undo() throws CannotUndoException {
		dstModel.remove(row);
		dstModel.add(row, oldData);
		table.tableChanged(new TableModelEvent(table.getModel(), row));
		other.tableChanged(new TableModelEvent(table.getModel(), row));
	}
	
	public void redo() throws CannotUndoException {
		dstModel.remove(row);
		dstModel.add(row, rowData);
		table.tableChanged(new TableModelEvent(table.getModel(), row));
		other.tableChanged(new TableModelEvent(table.getModel(), row));
	}

	public boolean canRedo(){return true;}
	public boolean canUndo(){return true;}
}
