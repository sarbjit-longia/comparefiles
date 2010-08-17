package com.merge.splitpane;

public class StringDataRow extends AbstractDataRow{

	String str = null;
	private static final long serialVersionUID = 1L;
	public StringDataRow(String str) {
		this.str = str;
		setText(str);
	}
	@Override
	public String getData() {
		return str;
	}
}