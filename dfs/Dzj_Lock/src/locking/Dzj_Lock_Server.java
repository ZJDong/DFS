package locking;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import locking.Dzj_Connection_Dao;

@Path("/lock")
public class Dzj_Lock_Server 
{
	@POST
	@Consumes({"application/json"})
	@Path("/getLockStatus")
	public String create(String input){
		Dzj_AuthServer_Properties.import_Properties();
		String reply = null;
		System.out.println("input = " + input);

		Dzj_Request_Lock lrequest = new Dzj_Request_Lock();
		lrequest = lrequest.getClassFromJson(input);

		String client_username = lrequest.getUsername();
		String client_filename = lrequest.getFilename();
		String client_email = lrequest.getEmail();
		String client_token = lrequest.getToken();
		
		Dzj__Lock lresponse = new Dzj__Lock();
		lresponse.setToken(client_token);
		lresponse.setEncryptedUsername(client_username);
		String jsonstr = lresponse.getJsonString();
		Dzj_Connecting_Auth_Server con = new Dzj_Connecting_Auth_Server();
		
		String respon = con.sendAuthRequest(jsonstr);
		Dzj_AuthServer rfas = new Dzj_AuthServer();
		System.out.println(" Value of respon is "+respon);
		rfas=rfas.getClassFromJson(respon);
		
		
		String key1 = rfas.getKey1();
		try {
		
		System.out.println("client_username value is"+client_username);
		System.out.println("client_filename value is"+client_filename);	
		System.out.println("client_email value is"+client_email);
		System.out.println("key1 value is"+key1);
		
	

		String auth = rfas.getStatus();
		Connection conn = Dzj_Connection_Dao.sqlconnect();
		Dzj__Client rtc = new Dzj__Client();
		
		// If the user is Authorized, check whether the file is accessible
		
		if (auth.equals("Y"))
		{
			String decrypt_client_filename = Dzj_Cryption.decrypt(client_filename, key1);
			String decrypt_client_email = Dzj_Cryption.decrypt(client_email, key1);
			
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select locked from lookup where filename = '" + decrypt_client_filename +"';");
			if(rs.next())
			{
				if(rs.getString(1).equals("Y")) // If the file is locked.
				{
					rtc.setLockstatus("N");
					
					reply= rtc.getJsonString();
					
				}
				else                  // If the file is not locked.
				{  			
					String query = "update lookup set locked = 'Y' where filename = '" + decrypt_client_filename +"';  ";
			        PreparedStatement preparedStmt = conn.prepareStatement(query);
			        preparedStmt.execute();
			        rtc.setLockstatus("Y");
			        reply=  rtc.getJsonString();
				}
				
			}
			else          // IF the file is not present, add the file.
			{
				String query = " insert into lookup values (?,?,?,'Y')";
		        PreparedStatement preparedStmt = conn.prepareStatement(query);
		        preparedStmt.setString (1, client_username);
		        preparedStmt.setString (2, decrypt_client_filename);
		        preparedStmt.setString (3, decrypt_client_email);
		        preparedStmt.execute();
		        rtc.setLockstatus("Y");
		        reply=  rtc.getJsonString();
				
			}
			
			
		}
		else // If the user is not authorized
		{	
			rtc.setAuthstatus("N");
			reply=  rtc.getJsonString();
		}
		
		}
		
		catch(SQLException sq) 
		{
			System.out.println("Exception in LockServer Select Lookup");
			sq.printStackTrace();
			
		} 
		
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
			
	}
	
	
	@POST
	@Consumes({"application/json"})
	@Path("/releaseLock")
	public String release(String input)
	{
		
		Dzj_AuthServer_Properties.import_Properties();
		String reply = null;
		
		Dzj_Request_Lock lrequest = new Dzj_Request_Lock();
		lrequest = lrequest.getClassFromJson(input);

		String client_filename = lrequest.getFilename();
		String client_username = lrequest.getUsername();
		String client_token = lrequest.getToken();
		
		Dzj__Lock lresponse = new Dzj__Lock();
		lresponse.setToken(client_token);
		lresponse.setEncryptedUsername(client_username);
		String jsonstr = lresponse.getJsonString();
		Dzj_Connecting_Auth_Server cas = new Dzj_Connecting_Auth_Server();
		
		String resp = cas.sendAuthRequest(jsonstr);
		Dzj_AuthServer rfas = new Dzj_AuthServer();
		rfas=rfas.getClassFromJson(resp);
		
		String key1 = rfas.getKey1();
		
		try {
						
			String auth = rfas.getStatus();
			Connection conn = Dzj_Connection_Dao.sqlconnect();
			Dzj__Client rtc = new Dzj__Client();
			if (auth.equals("Y"))
			{
				String decrypt_client_filename = Dzj_Cryption.decrypt(client_filename, key1);
				Statement stmt=conn.createStatement();
				
				ResultSet rs=stmt.executeQuery("select locked from lookup where filename = '" + decrypt_client_filename +"';");
				if(rs.next())
				{
					String query = "update lookup set locked = 'N' where filename = '" + decrypt_client_filename +"';  ";
			        PreparedStatement preparedStmt = conn.prepareStatement(query);
			        preparedStmt.execute();
			        rtc.setReleasestatus("0");
			        reply=  rtc.getJsonString();
				}
				
				else
				{
					rtc.setReleasestatus("0");
			        reply=  rtc.getJsonString();
				}
				
				
			}
			
			else
			{	
				rtc.setAuthstatus("N");
				reply=  rtc.getJsonString();
			}
				
						
		}
		
		catch(SQLException sq) 
		{			
			System.out.println("Exception in release lock in LockService"+sq);
			sq.printStackTrace();
			Dzj__Client rtc = new Dzj__Client();
			rtc.setReleasestatus("1");
	        reply=  rtc.getJsonString();
			
		} 
		
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reply;
	}

}
