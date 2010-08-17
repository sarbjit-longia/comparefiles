package com.merge.splitpane;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import javax.swing.JScrollBar;

public class ScrollbarListener implements AdjustmentListener{

	ArrayList<ScrollPane> comps = new ArrayList<ScrollPane>();  
	public static ScrollbarListener instance = null;
	
	private ScrollbarListener(){}
	
	public static ScrollbarListener getInstance(){
		if(instance == null){
			instance = new ScrollbarListener(); 
		}
		return instance;
	}
	
	public void registerScrollPane(ScrollPane  pane){
		pane.getVerticalScrollBar().addAdjustmentListener(instance);
		comps.add(pane);
	}
	/**************************************************************************
	 * This method will be called when adjustment of the scroll bar will happen 
	 * @param  none
	 * ************************************************************************/
	public void adjustmentValueChanged(AdjustmentEvent e) {
		try {
			/* Make sure we have data to be shown in the table */
			JScrollBar bar =  (JScrollBar)e.getSource();
			/* Make sure this is the value for all the scrollbars */
			for(ScrollPane pane: comps){
				if(pane.getVerticalScrollBar().getValue() + 1 <= pane.getVerticalScrollBar().getMaximum())
					pane.getVerticalScrollBar().setValue(bar.getValue());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

