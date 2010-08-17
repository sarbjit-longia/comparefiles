package com.merge.splitpane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import com.merge.toolbar.ToolBarButton;

public class FilePicker extends JPanel implements MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	JComboBox combo = null;
	ToolBarButton browse = null;
	ToolBarButton saveButton = null;
	Resources res = null;

	FilePicker(Resources res){
		this.res = res;
		combo  = new JComboBox();
		browse = new  ToolBarButton("MENU.FILE.OPEN.ICON");
		saveButton = new ToolBarButton("MENU.FILE.SAVE.ICON");
		browse.setBorder(BorderFactory.createEtchedBorder());
		saveButton.setBorder(BorderFactory.createEtchedBorder());
		browse.setContentAreaFilled(false);
		browse.addMouseListener(this);
		saveButton.setContentAreaFilled(false);
		saveButton.addMouseListener(this);
		GridBagLayout lay = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(lay);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 20;
		add(combo,c);
		c.weightx = .5;
		//c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		//c.weightx = 0.05;
		add(browse,c);
		c.gridx = 2;
		add(saveButton,c);
		browse.addActionListener(this);
		browse.setActionCommand("Browse");
		combo.addItem(res.getFileName());
		combo.setSelectedIndex(combo.getItemCount() - 1); 
		saveButton.addActionListener(this);
		saveButton.setActionCommand("Save");
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		btn.setBorder(BorderFactory.createEtchedBorder());
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		btn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		btn.setBorder(BorderFactory.createEtchedBorder());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Browse")){
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showOpenDialog(this);
			if(result == JFileChooser.APPROVE_OPTION){
				combo.addItem(chooser.getSelectedFile().getAbsolutePath());
				combo.setSelectedIndex(combo.getItemCount() - 1); 
				res.setFileName(chooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if(e.getActionCommand().equals("Save")){
			res.getLoader().save();
			saveButton.setEnabled(false);
		}
	}
}
