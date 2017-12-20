package locking;

import java.io.IOException;
import java.util.Properties;

public  class Dzj_AuthServer_Properties {

	public static String Url_IP;
	public static String Url_path;
	

	public static void import_Properties() {
		Properties config = new Properties();
		try 
		{
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Url.properties"));
			
			Url_IP = config.getProperty("Url_IP");
			Url_path = config.getProperty("Url_path");
									
		} catch (IOException e) {
			System.out.println("Unable to load the file"+e);
		}
	}
}