package com.merge.splitpane;

import javax.swing.JLabel;

public abstract class AbstractDataRow  extends JLabel implements DataRow{

	private static final long serialVersionUID = 1L;

	/**************************************************************************
	 * This method used to get data for a row in table
	 * @param  none
	 * ************************************************************************/
	abstract public String getData();
	
}
