package com.merge.splitpane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileAccess extends RandomAccessFile{

	private Lock fileLock = new ReentrantLock();
	public FileAccess(File file, String s) throws FileNotFoundException {
		super(file, s);
	}
	public FileAccess(String file, String s) throws FileNotFoundException {
		super(file, s);
	}
	
	public void lock(){
		fileLock.lock();
	}
	
	public void unLock(){
		fileLock.unlock();
	}
	
	public boolean tryLock(){ 
		return fileLock.tryLock();
	}
}
