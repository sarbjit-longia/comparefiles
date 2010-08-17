package com.merge.splitpane;

public class FileDataRow extends AbstractDataRow{

	private static final long serialVersionUID = 1L;
	long offset = -1;
	FileAccess racc = null;

	/**************************************************************************
	 * This is a constructor used to store the necessary info about the file
	 * @param  none
	 * ************************************************************************/
	public FileDataRow(long offset, FileAccess racc) {
		/* Get the node data */
		this.offset = offset;
		this.racc = racc;
		/* Set border to null */
		setBorder(null);
	}

	@Override
	public String getData() {
		/* Set the label text */
		String str = readData(); 
		setText(str);
		return str;
	}

	private String readData(){
		String str = null;
		if(racc.tryLock()){
			try{
				/* Seek to the position required and get the object from the position */
				racc.seek(offset);
				str = racc.readLine();
				racc.seek(racc.getFilePointer()-2);
				if(racc.read() == 13){
					str = str + "\r";
					if(racc.read() == 10)
						str = str + "\n";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				racc.unLock();
			}
		}
		return str;
	}
}
