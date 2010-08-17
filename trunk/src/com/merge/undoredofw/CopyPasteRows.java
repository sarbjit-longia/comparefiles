package com.merge.undoredofw;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import com.merge.listeners.ActionEventListeners;
import com.merge.splitpane.DataRow;
import com.merge.splitpane.Resources;
import com.merge.splitpane.StringDataRow;
import com.merge.splitpane.Table;

public class CopyPasteRows extends AbstractUndoableEdit{

	private static final long serialVersionUID = 1L;
	private String data = null;
	private int from, to;
	private List<DataRow> srcList = null;
	private Resources res = null;
	private ActionEventListeners listener = null;
	
	public CopyPasteRows(ActionEventListeners listener, Resources res, String data, List<DataRow> srcList, int at) {
		this.from = at;
		this.data = data;
		this.srcList = srcList;
		this.res = res;
		this.listener = listener;
		StringReader sr = new StringReader(data);
		BufferedReader br = new BufferedReader(sr);
		int i = at;
		try{
			String str = br.readLine();
			while(str != null){
				str = br.readLine();
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.to = i;
	}

	public void undo() throws CannotUndoException {
		int j = from;
		for(int i = from ; i < to; i++){
			srcList.remove(j);
		}
		listener.refresh();
		res.getTable().tableChanged(new TableModelEvent(res.getTable().getModel()));
		res.getOther().getTable().tableChanged(new TableModelEvent(res.getOther().getTable().getModel()));
	}

	public void redo() throws CannotUndoException {
		StringReader sr = new StringReader(data);
		BufferedReader br = new BufferedReader(sr);
		int i = from;
		try{
			String str = br.readLine();
			while(str != null){
				srcList.add(i, new StringDataRow(str));
				str = br.readLine();
				i++;
			}
			listener.refresh();
			res.getTable().tableChanged(new TableModelEvent(res.getTable().getModel()));
			res.getOther().getTable().tableChanged(new TableModelEvent(res.getOther().getTable().getModel()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean canRedo(){return true;}
	public boolean canUndo(){return true;}
}
