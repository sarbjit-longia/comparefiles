package com.merge.splitpane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.incava.util.diff.Difference;
import com.merge.algo.Algorithms;

public class TableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	FileReader in = null;
	BufferedReader buff = null;
	DataLoader loader = null;
	boolean showWhiteSpaces = false;
	boolean showLineNumbers = false;
	Resources res = null;

	TableModel(Resources res){
		super();
		this.loader =  res.getLoader();
		this.res = res;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		if(loader == null) return 0;
		return loader.getRowCount();
	}

	@Override
	public String getValueAt(int i, int j) {

		switch(j){
		case 0:
			if(showLineNumbers)
				return ""+(i+1);
			else
				return "";
		case 1:
			if(showWhiteSpaces){
				return convertToShowWhiteSpaces(colorString(i, j));
			}
			else{
				return colorString(i, j);
			}
		}
		return "";
	}

	public DataLoader getLoader() {
		return loader;
	}

	public void setLoader(DataLoader loader) {
		this.loader = loader;
	}

	public Class<?> getColumnClass(int column) {
		Object o = getValueAt(0, column);
		return (o == null)?EmptyDataRow.class:o.getClass();
	}

	public void setShowWhiteSpacesEnable(boolean value){
		showWhiteSpaces = value;
		fireTableDataChanged();
	} 
	public void setShowLineNumbers(boolean value){
		showLineNumbers = value;
		fireTableDataChanged();
	}

	private String convertToShowWhiteSpaces(String str){
		char glyphCRLF[] = {164, 182};
		char glyphCR[] = {164};
		char glyphLF[] = {182};
		String resStr = str;
		if(str != null){
			resStr = str.replace(' ', (char)183);
			if(str.indexOf("</html>") >= 0){
				if(resStr.contains("\r"))
					resStr = resStr.substring(0, resStr.indexOf("</html>")) + new String(glyphCR) + "</html>";
				if(resStr.contains("\n"))
					resStr = resStr.substring(0, resStr.indexOf("</html>")) + new String(glyphLF) + "</html>";
			}
			else{
				if(resStr.contains("\r"))
					resStr = resStr + new String(glyphCR);
				if(resStr.contains("\n"))
					resStr = resStr + new String(glyphLF);
			}
		}
		return resStr;
	}

	private String colorString(int row, int column){
		/* Color the values */
		String thisStr = loader.getRowAt(row);
		String otherStr = res.getOther().getLoader().getRowAt(row);
		List<Difference> out = Algorithms.getCommonCharacters(otherStr, thisStr);
		StringBuffer buff = new StringBuffer();
		if(out == null || otherStr.length() > thisStr.length()){
			return thisStr;
		}
		else{

			int count = 0;
			for(Difference dif : out){
				buff.append("<html>");
				if(dif.getDeletedEnd() != -1){
					for(int i = count; i < dif.getDeletedStart(); i++){
						buff.append(thisStr.charAt(i));
						count++;
					}
					buff.append("<font color=\"red\">");
					for(int i = dif.getDeletedStart(); i <= dif.getDeletedEnd(); i++){
						buff.append(thisStr.charAt(i));
						count++;
					}
					buff.append("</font>");
				}
				if(dif.getAddedEnd() != -1){
					for(int i = count; i < dif.getAddedStart(); i++){
						buff.append(thisStr.charAt(i));
						count++;
					}
					buff.append("<font color=\"red\">");
					for(int i = dif.getAddedStart(); i < dif.getAddedEnd(); i++){
						buff.append(thisStr.charAt(i));
						count++;
					}
					buff.append("</font>");
				}
			}
			for(int i = count ; i < thisStr.length(); i++) buff.append(thisStr.charAt(i));
			if(out!= null && out.size() > 0)
				buff.append("</html>");
		}
		return buff.toString();
	}

	public boolean isCellEditable(int i, int j){
		return false;
	}

}
