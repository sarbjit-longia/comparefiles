package com.merge.splitpane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.merge.listeners.ActionEventListeners;
import com.merge.util.Config;

public class ExitDialog extends JDialog{
	
	JCheckBox lCheck =null;
	JCheckBox rCheck =null;
	JButton save = null;
	JButton saveNone = null;
	JButton cancel = null;
	public ExitDialog(){
		setTitle(Config.getProperty("SAVE.CHANGES"));
		setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets= new Insets(10, 20, 0, 10);
		lCheck = new JCheckBox(Config.getProperty("MENU.FILE.SAVE.LEFT"));
		rCheck = new JCheckBox(Config.getProperty("MENU.FILE.SAVE.RIGHT"));
		save = new JButton(Config.getProperty("BTN.SAVE.CHECKED"));
		saveNone = new JButton(Config.getProperty("BTN.SAVE.NONE"));
		cancel = new JButton(Config.getProperty("BTN.CANCEL"));
		lCheck.setSelected(true);
		rCheck.setSelected(true);
		add(new JLabel(Config.getProperty("MSG.SAVE.CHANGES")), cons);
		cons.gridy = 1;
		add(lCheck, cons);
		cons.gridy = 2;
		add(rCheck, cons);
		cons.gridy = 3;
		cons.insets= new Insets(10, 2, 0, 10);
		cons.gridx = 1;
		add(save, cons);
		cons.gridx = 2;
		add(saveNone, cons);
		cons.gridx = 3;
		add(cancel, cons);
		pack();
		setLocationRelativeTo(null);
		
		save.addActionListener(ActionEventListeners.getInstance());
		save.setActionCommand("SaveFromExit");
		saveNone.addActionListener(ActionEventListeners.getInstance());
		saveNone.setActionCommand("SaveNoneFromExit");
		cancel.addActionListener(ActionEventListeners.getInstance());
		cancel.setActionCommand("CancelFromExit");
	}
	public void setState(Resources left, Resources right){
		if(!left.isDirty()){
			lCheck.setSelected(false);
			lCheck.setVisible(false);
		}
		if(!right.isDirty()){
			rCheck.setSelected(false);
			rCheck.setVisible(false);
		}
	}
	public boolean getLeftCheck() {
		return lCheck.isSelected();
	}
	public boolean getRightCheck() {
		return rCheck.isSelected();
	}
}
