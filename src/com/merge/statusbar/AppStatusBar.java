package com.merge.statusbar;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JPopupMenu.Separator;
import javax.swing.border.EtchedBorder;
import com.merge.util.MessageBox;

public class AppStatusBar extends JPanel{
	private static final long serialVersionUID = 1L;
	static JProgressBar pBar = new JProgressBar(0, 100);
	public AppStatusBar() {
		GridLayout lay = new GridLayout(1,4);
		setLayout(lay);
		add(new JLabel("Mode"));
		add(new JSeparator(Separator.VERTICAL));
		add(new JLabel("OnlineHelp"));
		add(new JSeparator(Separator.VERTICAL));
		pBar.setEnabled(false);
		add(pBar);		
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	public static void runTaskWithProgressBar(Runnable run){
		try{
			Thread th = new Thread(run);
			pBar.setEnabled(true);
			pBar.setIndeterminate(true);
			th.start();
		}catch (Exception e){
			MessageBox.showMessage(e.getMessage(), "Exception", MessageBox.ERROR_MESSAGE);
		}
	}
	
	public static void runTaskWithProgressBar(){
			pBar.setEnabled(true);
			pBar.setIndeterminate(true);
	}
	
	public static void notifyEndTaskWithProgressBar(){
		pBar.setIndeterminate(false);
		pBar.setEnabled(false);
	}
}
