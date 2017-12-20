package storage;

import java.io.IOException;
import java.util.Properties;

public  class Dzj_Url_Properties 
{
	public static String serverUrl;
	public static String authcheckUrl;
	public static void loadProperties() 
	{
		Properties config = new Properties();
		try 
		{
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			serverUrl = config.getProperty("serverUrl");
			authcheckUrl = config.getProperty("authcheckUrl");
			
		} 
		
		catch (IOException e) 
		{
			System.out.println("Failed to load property file"+e);
		}
	}
}
