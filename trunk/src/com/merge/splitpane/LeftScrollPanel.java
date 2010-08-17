package com.merge.splitpane;

import java.awt.Dimension;

import com.merge.listeners.SelectionListener;

public class LeftScrollPanel extends ScrollPane {

	private static final long serialVersionUID = 1L;
	Table table = null;
	
	public LeftScrollPanel(Resources res) {
		super();
		CellRenderer renderer = new CellRenderer(res);
		table = new Table(res.getModel());
		table.setDefaultRenderer(Object.class,renderer);
		table.setShowHorizontalLines(false);
		table.setDragEnabled(true);
		table.setIntercellSpacing(new Dimension(0,0));
		SelectionListener selListener = new SelectionListener(res);
		table.getSelectionModel().addListSelectionListener(selListener);
		table.addMouseListener(selListener);
		setViewportView(table);
		res.setTable(table);
		ScrollbarListener.getInstance().registerScrollPane(this);
	}
}
