package com.merge.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.merge.splitpane.Resources;
import com.merge.splitpane.Table;

/**
 * Class that will handle the selection of the cells.
 * It will select the same section in other panel depending on the user
 * selection in one panel.
 */
public class SelectionListener implements ListSelectionListener, MouseListener{

	/* Resource context */
	Resources res = null;
	public SelectionListener(Resources res) {
		this.res = res;
	}

	/**
	 * In case we change the selection, do the same in other panel
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}

	/**
	 * Decide which pane we selected the text in.
	 */
	private void setFocused(){
		res.setFocused(true);
		res.getOther().setFocused(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setFocused();
		Table other = res.getOther().getTable();
		other.removeRowSelectionInterval(0, other.getRowCount() - 1);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setFocused();
		Table other = res.getOther().getTable();
		other.removeRowSelectionInterval(0, other.getRowCount() - 1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
