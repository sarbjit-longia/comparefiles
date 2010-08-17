/******************************************************************************
 * This class will hold the data structure used to get data page wise
 * from the file.
 * @author Sarbjit Singh
 *****************************************************************************/
package com.merge.splitpane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader extends Thread {

	/* Streams to get the data from file */
	FileAccess racc = null;
	/* This hash map will store the mapping between the rows and the file
	 * pointers in the file.
	 */
	ArrayList<DataRow> map = new ArrayList<DataRow>(); 

	/**************************************************************************
	 * This is a constructor that opens the file for reading and also creates 
	 * the mapping between the rows and the file pointers
	 * @param  none
	 * ************************************************************************/
	public DataLoader(final String fileName) {
		if(fileName == null ) return;
		try{
			racc = new FileAccess(fileName,"rw");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run(){
		if(racc == null) return;
		getDataFromFile();
	}

	public void getDataFromFile(){
		try {
			int count = 0;
			racc.lock();
			/* Reset the data as this API is called from reload too */
			racc.seek(0);
			map.clear();
			while(true){
				/* Read the byte from the file */
				long filePointer = racc.getFilePointer();
				/* Put the starting file pointer to the label at that position */
				FileDataRow row = new FileDataRow(filePointer, racc);
				/* Skip number of bytes as read by the length parameter */
				String str = racc.readLine();
				if(str == null) break;
				map.add(count++, row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			racc.unLock();
		}
	}

	/**************************************************************************
	 * This is a method that returns the required columns data in a row
	 * @param  row
	 * ************************************************************************/
	public String getRowAt(int row) {
		if(map != null){
			if(map.get(row) != null)
				return map.get(row).getData(); 
		}
		return "";
	}

	/**************************************************************************
	 * This method returns the numbers of rows in model. It returns the size of
	 *  row index mappings that we created.
	 * @param  none
	 * ************************************************************************/
	public int getRowCount() {
		/* If map is not null return its count */
		if(map != null){
			//System.out.println("Rowsize:"+map.size());
			return map.size();
		}
		else{
			/* Else return 0 */
			return 0;
		}
	}

	public ArrayList<DataRow> getData() {
		return map;
	}

	public void setData(ArrayList<DataRow> map) {
		this.map = map;
	}

	private String getFileContents(){
		StringBuffer buff = new StringBuffer();
		for(DataRow row : map){
			if(row instanceof EmptyDataRow){
				buff.append("\r\n");
			}
			else{
				buff.append(row.getData());
			}
		}
		return buff.toString();
	}
	public void save(){

		if(racc == null) return;
		try{
			String con = getFileContents();
			racc.seek(0);
			racc.setLength(0);
			racc.writeBytes(con);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void saveAs(File file){
		try{
			FileWriter fw = new FileWriter(file);
			fw.write(getFileContents());
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

/******************************************************************************
 * 					End of file												  *
 *****************************************************************************/
