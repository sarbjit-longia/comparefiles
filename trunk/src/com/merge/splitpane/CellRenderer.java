package com.merge.splitpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import com.merge.util.Config;

public class CellRenderer implements TableCellRenderer{

	Resources res = null;
	int maxWidth = 0;
	public CellRenderer(Resources res) {
		this.res = res;
	}

	/**
	 * Class that compares the data in two tables and sets the background color
	 * @param table
	 * @param renderer
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean setBgColor(Table table, Component renderer, int row, int column) {
		String str = null;;
		if(res.getOther().getModel().getRowCount() > row)
			str = res.getOther().getModel().getValueAt(row, column);
		if( str!= null && !str.equals(table.getModel().getValueAt(row, column))){
			/* See if we want to get the color from config file */
			String clr = Config.getProperty("DIFF.BG.COLOR");
			if (clr != null){
				String c[] = clr.split(","); 
				renderer.setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
			}
			return false;
		}
		return true;
	}

	/**
	 * Method that provides the rendering information to the table
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		DefaultTableCellRenderer defRenderer = new DefaultTableCellRenderer();
		Component renderer = defRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		/* If its the line number cell return from here */
		if(column == 0){
			renderer.setFont(new Font(renderer.getFont().getFontName(), 0, 8));
			/* See if we want to get the color from config file */
			String clr = Config.getProperty("LN.NUM.BG.COLOR");
			if (clr != null){
				String c[] = clr.split(","); 
				renderer.setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
			}
			/* See if we want to get the color from config file */
			clr = Config.getProperty("LN.NUM.FG.COLOR");
			if (clr != null){
				String c[] = clr.split(","); 
				renderer.setForeground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
			}
			return renderer;
		}
		setBgColor((Table)table, renderer, row, column);
		if(isSelected){
			/* See if we want to get the color from config file */
			String clr = Config.getProperty("SELECTED.ROW.BG.COLOR");
			if (clr != null){
				String c[] = clr.split(","); 
				renderer.setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
			}
		}
//		if(value != null){
//			FontMetrics fm = table.getFontMetrics(table.getFont());
//			int strWidth = fm.stringWidth(value.toString());
//			if(maxWidth < strWidth)
//				maxWidth = strWidth;
//			table.getColumnModel().getColumn(column).setMinWidth(maxWidth);
//		}
		return renderer;
	}
}
