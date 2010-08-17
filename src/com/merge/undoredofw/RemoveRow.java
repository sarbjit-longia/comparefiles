package com.merge.undoredofw;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import com.merge.splitpane.DataRow;

public class RemoveRow extends AbstractUndoableEdit{

	private static final long serialVersionUID = 1L;
	private DataRow rowData = null;
	private int row;
	private ArrayList<DataRow> model = null;

	public RemoveRow(ArrayList<DataRow> model, DataRow data, int row){
		this.model = model;
		this.rowData = data;
		this.row = row;
	}

	public void undo() throws CannotUndoException {
		model.add(row, rowData);
	}
	
	public void redo() throws CannotUndoException {
		model.remove(row);
	}

	public boolean canRedo(){return true;}
	public boolean canUndo(){return true;}
}
