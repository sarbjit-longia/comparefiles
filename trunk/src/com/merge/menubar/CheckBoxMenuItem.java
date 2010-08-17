package com.merge.menubar;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;
import com.merge.util.Config;

/**
 * This class will implement the component of check box in menu items
 * @author singhs
 *
 */
public class CheckBoxMenuItem extends JCheckBoxMenuItem{

	private static final long serialVersionUID = 2564518831054953018L;

	/**
	 * Constructor with event
	 * @param label
	 * @param icon
	 * @param acc
	 * @param event
	 */
	CheckBoxMenuItem(String label, String icon, String acc, int event){
		super(Config.getProperty(label));
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
	CheckBoxMenuItem(String label, String icon, String acc){
		super(Config.getProperty(label));
		if(icon != null)
			setIcon(new ImageIcon(Config.getProperty(icon)));
		if(acc != null){
			setAccelerator(KeyStroke.getKeyStroke(Config.getProperty(acc)));
		}
	}
}
