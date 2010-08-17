package com.merge.splitpane;

import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class DataFilter {
	Resources res = null;
	
	/* The filter to show same lines in the table */
	RowFilter<Object, Object> sameFilter = new RowFilter<Object, Object>() {
		@SuppressWarnings("unchecked")
		public boolean include(Entry entry) {
			TableModel model = (TableModel)entry.getModel();
			TableModel other = res.getOther().getModel();
			int row = (Integer)entry.getIdentifier();
			String oString = null;
			String inString = model.getValueAt(row, 1);
			if(other.getRowCount() > row)
				oString = other.getValueAt(row, 1);
			if(inString != null){
				return inString.equals(oString);
			}
			return false;
		}
	};
	
	/* The filter to show only different lines in table */
	RowFilter<Object, Object> diffFilter = new RowFilter<Object, Object>() {
		@SuppressWarnings("unchecked")
		public boolean include(Entry entry) {
			TableModel model = (TableModel)entry.getModel();
			TableModel other = res.getOther().getModel();
			int row = (Integer)entry.getIdentifier();
			String oString = null;
			String inString = model.getValueAt(row, 1);
			if(other.getRowCount() > row)
				oString = other.getValueAt(row, 1);
			if(inString != null){
				return !inString.equals(oString);
			}
			return true;
		}
	};
	
	public DataFilter(Resources leftRes) {
		this.res = leftRes;
	}

	public void setOnlySameFilter(Table table, TableModel model){
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    sorter.setRowFilter(sameFilter);
	    table.setRowSorter(sorter);
	}
	public void setOnlyDifferentFilter(Table table, TableModel model){
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    sorter.setRowFilter(diffFilter);
	    table.setRowSorter(sorter);
	}
}
