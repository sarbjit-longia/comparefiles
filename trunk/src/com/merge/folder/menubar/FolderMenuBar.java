package com.merge.folder.menubar;

import java.awt.Color;

import javax.swing.JMenuBar;

import com.merge.util.Config;

/**
 * Class that creates the menu bar
 * @author singhs
 *
 */
public class FolderMenuBar extends JMenuBar{

	private static final long serialVersionUID = -2366574389780335690L;

	public FolderMenuBar(){ 
		super();
		add(new FileMenu());
		add(new EditMenu());
		add(new ViewMenu());
		add( new SearchMenu());
		add(new HelpMenu());
		/* See if we want to get the color from config file */
		String clr = Config.getProperty("MENU.BG.COLOR");
		if (clr != null){
			String c[] = clr.split(","); 
			setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
		}
		setBorderPainted(true);
		setBorder(null);
	}
}
