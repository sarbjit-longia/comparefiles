package com.merge.menubar;

import java.awt.Color;

import javax.swing.JMenu;

import com.merge.util.Config;

/**
 * This class is the base class of every menu
 * @author singhs
 *
 */
public abstract class Menu extends JMenu {
	
	private static final long serialVersionUID = 1L;
	public Menu(String name){
		super(name);
		/* See if we want to get the color from config file */
		String clr = Config.getProperty("MENU.BG.COLOR");
		if (clr != null){
			String c[] = clr.split(","); 
			setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
		}
	}
}
