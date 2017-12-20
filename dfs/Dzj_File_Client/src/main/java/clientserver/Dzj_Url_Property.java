package clientserver;

import java.io.IOException;
import java.util.Properties;

public  class Dzj_Url_Property 
{
	public static String loginUrl;
	public static String readfileUrl;
	public static String directoryinfo_Url;
	public static String serverUrl;
	public static String directoryServerUrl;
	public static String writefileUrl;
	public static String lockServerUrl;
	public static String lockingUrl;
	public static String cache_Time;
	public static String fileServerUrl;
	public static String unlockingUrl;

	public static void import_Properties() 
	{
		Properties config = new Properties();
		try 
		{
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			
			
			serverUrl = config.getProperty("serverUrl");
			directoryServerUrl = config.getProperty("directoryServerUrl");
			loginUrl = config.getProperty("loginUrl");
			readfileUrl = config.getProperty("readfileUrl");
			directoryinfo_Url = config.getProperty("directoryinfoUrl");
			writefileUrl = config.getProperty("writefileUrl");
			cache_Time = config.getProperty("cache_Time");
			lockServerUrl = config.getProperty("lockServerUrl");
			lockingUrl = config.getProperty("lockingUrl");
			fileServerUrl = config.getProperty("fileServerUrl");
			unlockingUrl = config.getProperty("unlockingUrl");
		} 
		
		catch (IOException e) 
		{
			System.out.println("Failed to import the property file"+e);;
		}
	}
}
