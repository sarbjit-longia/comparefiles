package com.merge.splitpane;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class RightPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	FilePicker fPicker = null;
	
	public RightPanel(Resources res) {
		setLayout(new BorderLayout());
		fPicker =  new FilePicker(res);
		add(fPicker, BorderLayout.NORTH);
		add(new RightScrollPane(res));
	}
}
