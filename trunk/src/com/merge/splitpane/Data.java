package com.merge.splitpane;

import java.util.ArrayList;

class Data{
	ArrayList<DataRow> uiData = new ArrayList<DataRow>();
	private boolean needProcessing = true;
	public ArrayList<DataRow> getUiData() {
		return uiData;
	}
	public void setUiData(ArrayList<DataRow> uiData) {
		this.uiData = uiData;
	}
	public boolean isNeedProcessing() {
		return needProcessing;
	}
	public void setNeedProcessing(boolean needProcessing) {
		this.needProcessing = needProcessing;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	Table table;
}