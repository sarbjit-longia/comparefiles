package com.merge.util;

import javax.swing.JOptionPane;

public class MessageBox {
	public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
	static final int INFO = JOptionPane.INFORMATION_MESSAGE;

	public static void showMessage(String str, String title, int type ){
		JOptionPane.showMessageDialog(null, str, title, type);
	}
}
