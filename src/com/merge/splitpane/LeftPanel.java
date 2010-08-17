package com.merge.splitpane;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class LeftPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	FilePicker fPicker = null;
	
	public LeftPanel(Resources res) {
		setLayout(new BorderLayout());
		fPicker =  new FilePicker(res);
		add(fPicker, BorderLayout.NORTH);
		add(new LeftScrollPanel(res));
	}
}
