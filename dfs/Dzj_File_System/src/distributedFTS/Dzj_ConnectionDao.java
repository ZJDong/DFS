package distributedFTS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connection to get data access from database
public class Dzj_ConnectionDao 
{
	
	public static Connection sqlconnect() 
	{
		Connection connect= null;
	try 
	{
		Class.forName("com.mysql.jdbc.Driver");
	    connect=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/loginserver","root","Dublin12*");
	} 
	
	catch (ClassNotFoundException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	catch (SQLException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return connect;
	}

}
