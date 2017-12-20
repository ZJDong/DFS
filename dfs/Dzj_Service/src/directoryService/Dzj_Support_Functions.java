package directoryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import directoryService.Dzj_DatabaseProperties;


public class Dzj_Support_Functions 
{

	public  Connection sqlconnect() 
	{
		Connection con= null;
		Dzj_DatabaseProperties.loadProperties();
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(  
					"jdbc:mysql://"+Dzj_DatabaseProperties.dbip+":"+
			Dzj_DatabaseProperties.dbport+"/"+Dzj_DatabaseProperties.dbname+
			"?useSSL=false",
			Dzj_DatabaseProperties.dbusername,
			Dzj_DatabaseProperties.dbpassword);
			System.out.println(con);
		} 

		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return con;
	}

	public String connection(String url, String input,String type) 
	{
		String output="";
		String reply="";
		try 
		{
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(url);
			System.out.println("*******\nSending "+type+" Request:\n"+input+"\n*********");
			System.out.println("*******\nSending "+type+" Request to "+url+"\n**********");

			postRequest.addHeader("accept", "application/json");
			StringEntity se = new StringEntity(input.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			postRequest.setEntity(se);
			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			while ((output = br.readLine()) != null) 
			{
				reply+=output;
			}
			
			System.out.println("*******\nGot  "+type+" Response:\n"+reply+"\n*********");

		}
		
		catch(MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		catch(IOException e) 
		{

			e.printStackTrace();

		}
		return reply;
	}
	
	public String sendAuthCheckRequest(String input) 
	{
		String url = Dzj_DatabaseProperties.serverUrl+Dzj_DatabaseProperties.authcheckUrl;
		String type = "AuthCheck";
		return connection(url,input,type);
	}
	
	public HashMap<String, String> getFileLocationForRead(String filename) 
	{
		HashMap<String, String> filestats = new HashMap<String, String>();
		ResultSet rs;
		try 
		{
			Connection conn = sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select server,directory from dirservice.filelist where filename like '" + filename +"';");
			if(rs.next())
			{
				System.out.println("\n\n########################\nServer:: "+ rs.getString(1)+ "Dir:: "+ rs.getString(2));
				filestats.put("serverurl", rs.getString(1));
				filestats.put("directory", rs.getString(2));
			}
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return filestats;
	}

	
	public HashMap<String, String> getFileLocationForWrite(String filename) 
	{
		HashMap<String, String> filestats = new HashMap<String, String>();
		ResultSet rs;
		try 
		{
			Connection conn = sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select server,directory from dirservice.filelist where filename like '" + filename +"';");
			if(rs.next())
			{
				System.out.println("\n\n########################\nServer:: "+ rs.getString(1)+ "Dir:: "+ rs.getString(2));
				filestats.put("serverurl", rs.getString(1));
				filestats.put("directory", rs.getString(2));
			}
			
			else 
			{
				String query = " insert into dirservice.filelist values (?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString (1, filename);
				preparedStmt.setString (2, "http://127.0.0.1:8085/");
				preparedStmt.setString (3, "N");
				preparedStmt.setString (4, "");
				preparedStmt.setString (5, "Sankalp\\");
				preparedStmt.execute();
				filestats.put("serverurl","http://127.0.0.1:8085/");
				filestats.put("directory", "Sankalp\\");
			}
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return filestats;

	}

	//Method returns all the filenames in the db
	public HashMap<String, String> getCompleteFileList() 
	{
		HashMap<String, String> filestats = new HashMap<String, String>();
		ResultSet rs;
		String filenameList="";
		String directoryList="";
		try 
		{
			Connection conn = sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select filename,directory from dirservice.filelist;");
			while(rs.next())
			{
				filenameList=rs.getString(1)+","+filenameList;
				directoryList=rs.getString(2)+","+directoryList;
			}
			filestats.put("filename", filenameList);
			filestats.put("directory", directoryList);
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return filestats;
	}


}