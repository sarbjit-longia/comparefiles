package com.merge.util;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	static  Config instance = null;
	private static Properties props = null;
	private Config(){}
	public static Config getInstance(){
		if(instance == null){
			try{
				props = new Properties();
				props.load(new FileInputStream("C://workspace//onlinemerge//src//config.properties"));
				return new Config();
			}catch(Exception e){
				MessageBox.showMessage("Config file not found", "File not found", MessageBox.ERROR_MESSAGE);
				System.exit(-1);
			}
		}
		return instance;
	}
	
	static public String getProperty(String name){
		getInstance();
		return props.getProperty(name);
	}
/**************************** Unit Test Cases *******************************/
	public static void main(String args[]){
		Config c = Config.getInstance();
		System.out.println(c.getProperty("LN.NUM.BG.COLOR"));
	}
}
