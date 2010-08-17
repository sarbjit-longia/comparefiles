package com.merge.clipboard;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import com.merge.splitpane.DataRow;

public class ClipBoardData implements Transferable{

	String data;
	public ClipBoardData(ArrayList<DataRow> list) {
		StringBuffer buff = new StringBuffer();
		for(DataRow row : list){
			buff.append(row.getData());
		}
		//buff.replace(buff.toString().lastIndexOf("\n"),buff.length(),"\n");
		data = buff.toString();
	}
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return data;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{DataFlavor.stringFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return DataFlavor.stringFlavor.equals(flavor);
	}
}
