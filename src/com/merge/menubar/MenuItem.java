package com.merge.menubar;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.merge.util.Config;

public class MenuItem extends JMenuItem{

	private static final long serialVersionUID = 2564518831054953018L;

	/**
	 * Constructor where we specify which event will make it checked
	 * @param label
	 * @param icon
	 * @param acc
	 * @param event
	 */
	MenuItem(String label, String icon, String acc, int event){
		super(Config.getProperty(label), event);
		if(icon != null)
			setIcon(new ImageIcon(Config.getProperty(icon)));
		if(acc != null){
			setAccelerator(KeyStroke.getKeyStroke(Config.getProperty(acc)));
		}
	}
	
	/**
	 * Constructor without event
	 * @param label
	 * @param icon
	 * @param acc
	 */
	MenuItem(String label, String icon, String acc){
		super(Config.getProperty(label));
		if(icon != null)
			setIcon(new ImageIcon(Config.getProperty(icon)));
		if(acc != null){
			setAccelerator(KeyStroke.getKeyStroke(Config.getProperty(acc)));
		}
	}
}
