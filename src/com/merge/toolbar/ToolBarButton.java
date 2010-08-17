package com.merge.toolbar;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import com.merge.util.Config;

public class ToolBarButton extends JButton {

	private static final long serialVersionUID = 1L;

	public ToolBarButton(String icon) {
		super(new ImageIcon(Config.getProperty(icon)));
		/* See if we want to get the color from config file */
		String clr = Config.getProperty("TOOLBAR.BG.COLOR");
		if (clr != null){
			String c[] = clr.split(","); 
			setBackground(new Color(Integer.parseInt(c[0]),Integer.parseInt(c[1]),Integer.parseInt(c[2])));
		}
		setMargin(new Insets(3, 1, 2, 1));
		setFocusable(false);
	}
}
