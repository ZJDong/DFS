package directoryService;

import java.io.IOException;
import java.util.Properties;

public  class Dzj_DatabaseProperties 
{
	public static String serverUrl;
	public static String authcheckUrl;
	public static String dbusername;
	public static String dbpassword;
	public static String dbip;
	public static String dbport;
	public static String dbname;
	public static void loadProperties() 
	{
		Properties db_config = new Properties();
		try 
		{
			db_config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db_config.properties"));
			serverUrl = db_config.getProperty("serverUrl");
			authcheckUrl = db_config.getProperty("authcheckUrl");
			dbusername = db_config.getProperty("dbusername");
			dbpassword = db_config.getProperty("dbpassword");
			dbip = db_config.getProperty("dbip");
			dbport = db_config.getProperty("dbport");
			dbname = db_config.getProperty("dbname");
			
		} 
		
		catch (IOException e) 
		{
			System.out.println("Failed to load property file"+e);
		}
	}
}
