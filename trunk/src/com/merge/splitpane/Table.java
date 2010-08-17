package com.merge.splitpane;

import javax.swing.JTable;

public class Table extends JTable{

	private static final long serialVersionUID = 1L;
	
	Table(TableModel model){
		super(model);
		getColumnModel().getColumn(0).setMaxWidth(5);
		getColumnModel().getColumn(0).setResizable(false);
		setTableHeader(null);
		getSelectionModel().setSelectionInterval(0, 0);
		//setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}
