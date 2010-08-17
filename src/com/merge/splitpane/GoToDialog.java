package com.merge.splitpane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

@SuppressWarnings("serial")
public class GoToDialog extends JDialog{
	JTextField text = null;
	JButton go = null;
	public GoToDialog() {
		super();
		setTitle(Config.getProperty("GOTO.TITLE"));
		setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		setAlwaysOnTop(true);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets= new Insets(10, 20, 20, 10);
		add(new JLabel(Config.getProperty("GOTO.LINE.NUMBER")), cons);
		cons.gridx = 1;
		text = new JTextField();
		text.setPreferredSize(new Dimension(50,(int)text.getPreferredSize().getHeight()));
		add(text, cons);
		cons.gridx = 2;
		cons.insets= new Insets(10, 10, 20, 10);
		go = new JButton(Config.getProperty("GO"));
		go.setActionCommand("GoToLineNumber");
		go.addActionListener(ActionEventListeners.getInstance());
		add(go,cons);
		pack();
		setLocationRelativeTo(null);
	}
	public int getLineNumber(){
		try{
			if(text.getText()!=null){
				return Integer.parseInt(text.getText());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
