package com.merge.splitpane;

public class EmptyDataRow extends AbstractDataRow{

	private static final long serialVersionUID = 1L;

	@Override
	public String getData() {
		setText(null);
		return null;
	}

}
