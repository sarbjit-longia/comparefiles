package com.merge.splitpane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.merge.toolbar.FindButton;

public class FindDialog extends JDialog{

	private JComboBox input = null;
	private JButton findButton = null;
	private JButton cancelButton = null;
	private JCheckBox ignoreCase = null;
	private JCheckBox wholeWord = null;
	private JCheckBox regex = null;
	private JCheckBox searchUp = null;
	private JRadioButton findLeft = null;
	private JRadioButton findRight = null;
	private JRadioButton findBoth = null;
	private ActionListener listener = null;
	
	public FindDialog(ActionListener listener) {
		super();
		this.listener = listener;
		setTitle("Find");
		setAlwaysOnTop(true);
		setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets = new Insets(2, 2, 2, 2);
		add(new JLabel("Text to find:"), cons);
		input = new JComboBox();
		input.setEditable(true);
		cons.gridy = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		add(input, cons);
		ignoreCase = new JCheckBox("Ignore Case");
		ignoreCase.setSelected(true);
		wholeWord = new JCheckBox("Match whole word");
		regex = new JCheckBox("Regular Expression");
		regex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(regex.isSelected())
					wholeWord.setEnabled(false);
				else
					wholeWord.setEnabled(true);
			}
		});
		searchUp = new JCheckBox("Search Up");
		cons.gridwidth = 1;
		cons.gridy = 2;
		cons.weightx = 1;
		add(ignoreCase, cons);
		cons.gridy = 3;
		add(wholeWord, cons);
		cons.gridy = 4;
		add(regex, cons);
		cons.gridy = 5;
		add(searchUp, cons);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Sides"));
		panel.setLayout(new FlowLayout());
		ButtonGroup radioGroup = new ButtonGroup();
		findBoth = new JRadioButton("Both");
		findLeft = new JRadioButton("Left");
		findRight = new JRadioButton("Right");
		findBoth.setSelected(true);
		radioGroup.add(findLeft);
		radioGroup.add(findBoth);
		radioGroup.add(findRight);
		panel.add(findLeft, FlowLayout.LEFT);
		panel.add(findRight, FlowLayout.LEFT);
		panel.add(findBoth, FlowLayout.LEFT);
		cons.gridx = 0;
		cons.gridy = 6;
		cons.weightx = 3;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		add(panel, cons);
		cons.gridwidth = 1;;
		cons.gridy = 7;
		cons.gridx = 1;
		cons.weightx = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		findButton = new JButton("Find");
		findButton.setActionCommand("Find");
		findButton.addActionListener(listener);
		add(findButton, cons);
		cons.gridx = 2;
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(listener);
		add(cancelButton, cons);
		pack();
		setLocationRelativeTo(null);
	}
	public boolean isCaseInsensitive(){
		return ignoreCase.isSelected();
	}
	public boolean isMatchWholeWord(){
		return wholeWord.isSelected();
	}
	public boolean isRegex(){
		return regex.isSelected();
	}
	public boolean isFindInLeft(){
		return findLeft.isSelected();
	}
	public boolean isFindInRight(){
		return findRight.isSelected();
	}
	public boolean isFindinBoth(){
		return findBoth.isSelected();
	}
	public String getSearchString(){
		return (String)input.getSelectedItem();
	}
	public boolean isSearchUp(){
		return searchUp.isSelected();
	}
	
}

