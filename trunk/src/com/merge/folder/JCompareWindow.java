/*
 * The MIT License
 * 
 * Copyright (c) <year> <copyright holders>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*
 * JCompareWindow.java
 *
 * Created on Sep 3, 2009, 10:15:22 PM
 */
package com.merge.folder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import com.merge.listeners.ActionEventListeners;

/**
 *
 * @author Tushar Joshi <tusharvjoshi@gmail.com>
 */
public class JCompareWindow extends javax.swing.JPanel {

	/** Creates new form JCompareWindow */
	public JCompareWindow() {
		initComponents();
		//leftTree.addTreeSelectionListener(ActionEventListeners.getInstance());
		leftTree.setCellRenderer(new MyCellRenderer());
		leftTree.setScrollsOnExpand(false);
		rightTree.setCellRenderer(new MyCellRenderer());
		rightTree.setScrollsOnExpand(false);
		//rightTree.addTreeSelectionListener(ActionEventListeners.getInstance());
		leftTree.setModel(null);
		rightTree.setModel(null);

		leftPathField.setText("");
		rightPathField.setText("");

		leftTree.addTreeExpansionListener(new TreeExpansionListener() {

			public void treeExpanded(TreeExpansionEvent event) {
				int row = leftTree.getRowForPath(event.getPath());
				leftTree.setSelectionRow(row);
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				int row = leftTree.getRowForPath(event.getPath());
				leftTree.setSelectionRow(row);
			}
		});

		rightTree.addTreeExpansionListener(new TreeExpansionListener() {

			public void treeExpanded(TreeExpansionEvent event) {
				int row = rightTree.getRowForPath(event.getPath());
				rightTree.setSelectionRow(row);
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				int row = rightTree.getRowForPath(event.getPath());
				rightTree.setSelectionRow(row);
			}
		});

		leftTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ActionEventListeners.getInstance().setSelectedTree(leftTree);
				int row = leftTree.getRowForPath(leftTree.getSelectionPath());
				rightTree.setSelectionRow(row);
				if (!leftTree.isCollapsed(leftTree.getSelectionPath())) {
					rightTree.expandRow(row);
				} else {
					rightTree.collapseRow(row);
				}
				if(e.getClickCount() >= 2){
					LeftItem itemLeft = (LeftItem)leftTree.getSelectionPath().getLastPathComponent();
					RightItem itemRight = (RightItem)rightTree.getSelectionPath().getLastPathComponent();
					if(itemLeft.isLeaf() && itemRight.isLeaf()){
						String leftFile = itemLeft.getItem().getLeftPath()+File.separator+itemLeft.getItem().getName();
						String rightFile = itemRight.getItem().getRightPath()+File.separator+itemRight.getItem().getName();
						ActionEventListeners.getInstance().handleOpenFiles(leftFile, rightFile);
					}
				}
				//setRightScroll();
			}
		});

		rightTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ActionEventListeners.getInstance().setSelectedTree(rightTree);
				int row = rightTree.getRowForPath(rightTree.getSelectionPath());
				leftTree.setSelectionRow(row);
				if (!rightTree.isCollapsed(rightTree.getSelectionPath())) {
					leftTree.expandRow(row);
				} else {
					leftTree.collapseRow(row);
				}
				if(e.getClickCount() >= 2){
					LeftItem itemLeft = (LeftItem)leftTree.getSelectionPath().getLastPathComponent();
					RightItem itemRight = (RightItem)rightTree.getSelectionPath().getLastPathComponent();
					if(itemLeft.isLeaf() && itemRight.isLeaf()){
						String leftFile = itemLeft.getItem().getLeftPath()+File.separator+itemLeft.getItem().getName();
						String rightFile = itemRight.getItem().getRightPath()+File.separator+itemRight.getItem().getName();
						ActionEventListeners.getInstance().handleOpenFiles(leftFile, rightFile);
					}
				}
				//setLeftScroll();
			}
		});

		leftPathField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					comparePaths();
				}
			}
		});

		rightPathField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					comparePaths();
				}
			}
		});

		JScrollBar leftScrollBar = leftScrollPane.getVerticalScrollBar();
		JScrollBar rightScrollBar = rightScrollPane.getVerticalScrollBar();

		MyChangeListener listener = new MyChangeListener(leftScrollBar.getModel());
		rightScrollBar.getModel().addChangeListener( listener );

		listener = new MyChangeListener(rightScrollBar.getModel());
		leftScrollBar.getModel().addChangeListener( listener );
	}

	class MyChangeListener implements ChangeListener {

		private BoundedRangeModel myModel;

		/**
		 *  @param   model  This model is forced to move in synchronization
		 *  to this ChangeListener's event-source.
		 */
		public MyChangeListener(BoundedRangeModel model) {
			myModel = model;
		}

		// - begin  implementation of ChangeListener
		//
		/**    Envoked when the target of the listener has changed its state.
		 */
		public void stateChanged(ChangeEvent e) {
			BoundedRangeModel sourceModel = (BoundedRangeModel) e.getSource();

			int sourceDiff = sourceModel.getMaximum() - sourceModel.getMinimum();
			int destDiff = myModel.getMaximum() - myModel.getMinimum();
			int destValue = sourceModel.getValue();

			if (sourceDiff != destDiff) {
				destValue = (destDiff * sourceModel.getValue()) / sourceDiff;
			}

			myModel.setValue(myModel.getMinimum() + destValue);
		}
		//
		// - end  implementation of ChangeListener
	}

	private void setLeftScroll() {
		int row = rightTree.getRowForLocation(0, 0);
		TreePath path = leftTree.getPathForRow(row);
		leftTree.scrollPathToVisible(path);
	}

	private void setRightScroll() {
		int row = leftTree.getRowForLocation(0, 0);
		TreePath path = rightTree.getPathForRow(row);
		rightTree.scrollPathToVisible(path);
	}

	public void setPaths(String leftPath, String rightPath) {
		leftPathField.setText(leftPath);
		rightPathField.setText(rightPath);
		comparePaths();
		title = leftPath.substring(leftPath.lastIndexOf(File.separator) + 1) +" - "+ rightPath.substring(rightPath.lastIndexOf(File.separator) + 1);
	}
	
	public void comparePaths() {
		String leftPath = leftPathField.getText();
		String rightPath = rightPathField.getText();
		Item item = new ItemImpl();
		item.setLeftPath(leftPath);
		item.setRightPath(rightPath);
		item.setName("");
		item.setLeaf(false);
		JCompare.compare(item);
		LeftTreeModel leftModel = new LeftTreeModel(item);
		leftTree.setModel(leftModel);
		RightTreeModel rightModel = new RightTreeModel(item);
		rightTree.setModel(rightModel);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		leftScrollPane = new javax.swing.JScrollPane();
		leftTree = new javax.swing.JTree();
		rightScrollPane = new javax.swing.JScrollPane();
		rightTree = new javax.swing.JTree();
		leftPathField = new javax.swing.JTextField();
		rightPathField = new javax.swing.JTextField();

		//setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		leftScrollPane.setViewportView(leftTree);

		rightScrollPane.setViewportView(rightTree);

		leftPathField.setText("jTextField1");

		rightPathField.setText("jTextField2");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		//getContentPane().setLayout(layout);
		setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(leftPathField, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
								.addComponent(leftScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(rightScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
										.addComponent(rightPathField, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(leftPathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(rightPathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(leftScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
										.addComponent(rightScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
										.addContainerGap())
		);

		//pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new JCompareWindow().setVisible(true);
			}
		});
	}
	public String getTitle(){
		return title;
	}
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField leftPathField;
	private javax.swing.JScrollPane leftScrollPane;
	private javax.swing.JTree leftTree;
	private javax.swing.JTextField rightPathField;
	private javax.swing.JScrollPane rightScrollPane;
	private javax.swing.JTree rightTree;
	private String title;
	// End of variables declaration//GEN-END:variables
	public javax.swing.JTree getLeftTree() {
		return leftTree;
	}

	public javax.swing.JTree getRightTree() {
		return rightTree;
	}

	public void setLeftTree(javax.swing.JTree leftTree) {
		this.leftTree = leftTree;
	}

	public void setRightTree(javax.swing.JTree rightTree) {
		this.rightTree = rightTree;
	}
}
