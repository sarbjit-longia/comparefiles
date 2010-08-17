package com.merge.splitpane;

public class Resources {
	
	DataLoader loader = null;
	TableModel model = null;
	Resources other = null;
	Table table = null;
	boolean focused = true;
	String fileName = null;
	boolean isDirty = false;
	
	public DataLoader getLoader() {
		return loader;
	}
	public void setLoader(DataLoader loader) {
		this.loader = loader;
	}
	public TableModel getModel() {
		return model;
	}
	public void setModel(TableModel model) {
		this.model = model;
	}
	public Resources getOther() {
		return other;
	}
	public void setOther(Resources other) {
		this.other = other;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public boolean isFocused() {
		return focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	public String getFileName() { 
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isDirty() {
		return isDirty;
	}
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	
}
