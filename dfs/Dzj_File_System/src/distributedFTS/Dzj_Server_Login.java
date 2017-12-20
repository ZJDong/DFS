package distributedFTS;

/**
 * @author Sankalp
 *
 */

import java.io.*;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


// Secure Login Request is Processed
@Path("/fts")
public class Dzj_Server_Login 
{
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String login(String input){


		Dzj_Request_Login lrequests = new Dzj_Request_Login();
		lrequests = lrequests.getClassFromJson(input);

		String client_uname = lrequests.getUsername();
		String client_passwd = lrequests.getPassword();


		Dzj_Response_Login lresponse = new Dzj_Response_Login();
		
		// Authenticating the login user 
		try

		{

			Connection connect = Dzj_ConnectionDao.sqlconnect(); 
			Statement stmtConnect=connect.createStatement();  
			ResultSet rs=stmtConnect.executeQuery("select fname,usertype,token,pswd from users where encrypt_username = '" + client_uname +"';");

			if(rs.next())
			{

				// If username gets Authenicated, token and key 1 is sent to client as response
				if(rs.getString(4).equals(Dzj_Cryption.decrypt(client_passwd, rs.getString(4))))
				{
					lresponse.setStatus("Y");
					lresponse.setName(rs.getString(1));
					lresponse.setUser_type(rs.getString(2));
					lresponse.setToken(rs.getString(3));
					
					 String key1 = Dzj_Cryption.getInitialKey();
					 String key2 = Dzj_Cryption.getInitialKey();
					 String time = String.valueOf(System.currentTimeMillis());
					 String myToken = lresponse.getName() + ";;" + key1 + ";;" + time;
					
					 lresponse.setToken(Dzj_Cryption.encrypt(myToken,key2));
					 lresponse.setKey1(key1);
					 String query = "update users set key1 = ?, key2 = ?, token = ? where encrypt_username = '" + client_uname +"';  ";
				        PreparedStatement preparedstmtConnect = connect.prepareStatement(query);
				        preparedstmtConnect.setString (1, key1);
				        preparedstmtConnect.setString (2, key2);
				        preparedstmtConnect.setString (3, lresponse.getToken());
				        preparedstmtConnect.execute();
					return lresponse.getJsonString();
				}


			}

			// Authentication gets rejected and the Status is sent as N
			lresponse.setStatus("N");
			connect.close();  
		}

		catch(Exception e)
		{

			System.out.println("Exception in main"+e);

		}  

		return lresponse.getJsonString();
	}

	
	// Authentication of the token
	
	@POST
	@Consumes({"application/json"})
	@Path("/tokenCheck")
	public String tokenCheck(String input){
		
		Dzj_Request_Token tk_request = new Dzj_Request_Token();
		tk_request = tk_request.getClassFromJson(input);
		
		String client_uname = tk_request.getEncryptedUsername();
		String client_token = tk_request.getToken();
		
		System.out.println("username is "+client_uname);
		System.out.println("token is "+client_token);

		Dzj_Response_Token tresponse = new Dzj_Response_Token();
		Connection connect = Dzj_ConnectionDao.sqlconnect();
		try 
		{
			Statement stmtConnect=connect.createStatement();  
			ResultSet rs=stmtConnect.executeQuery("select token,key2,key1 from users where token = '" + client_token +"' and encrypt_username = '" + client_uname +"';");
		
		if(rs.next())
		{
			client_token = Dzj_Cryption.decrypt(client_token, rs.getString(2));
			StringTokenizer st = new StringTokenizer(client_token,";;");
			String ds_ttl = new String();
			while (st.hasMoreTokens()) 
		    {  
		    	 ds_ttl = st.nextToken();  
		    }
		     
		    long a = Long.parseLong(ds_ttl);
		    System.out.println(""+a);
		    long b = System.currentTimeMillis();
		    
		    if(b - a <= 300000)
		    {
		    	tresponse.setStatus("Y");
		 		tresponse.setKey1(rs.getString(3));
		 		return tresponse.getJsonString();
		    }
			
				
		}			
		
		tresponse.setStatus("N");
		return tresponse.getJsonString();	
		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tresponse.setStatus("N");
		return tresponse.getJsonString();


	}	

}








