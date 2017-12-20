package storage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import storage.Dzj_Support_Functions;
import storage.Dzj_Request_AuthCheck;
import storage.Dzj_Response_AuthCheck;
import storage.Dzj_Url_Properties;
import storage.Dzj_Request_ReadFile;
import storage.Dzj_Response_ReadFile;
import storage.Dzj_Request_WriteFile;
import storage.Dzj_Response_WriteFile;
import storage.Dzj_Encrypt_Decrypt;

@Path("/reader")
public class Dzj_Request_Handler 
{
	
	@POST
	@Consumes({"application/json"})
	@Path("/readFile")
	public String readFile(String input) 
	{
		Dzj_Url_Properties.loadProperties();
		Dzj_Request_ReadFile read_Request = new Dzj_Request_ReadFile();
		read_Request = read_Request.getClassFromJsonString(input);
		String token = read_Request.getToken();
		String encUsername = read_Request.getEncryptedUsername();
		String directory = read_Request.getDirectory();
		String filename = read_Request.getFilename();
		
		//Send request to AS to check token
		Dzj_Request_AuthCheck checkReq = new Dzj_Request_AuthCheck();
		Dzj_Response_AuthCheck checkResponse = new Dzj_Response_AuthCheck();
		Dzj_Support_Functions sf = new Dzj_Support_Functions();
		Dzj_Response_ReadFile readResponse = new Dzj_Response_ReadFile();
		String path = "";
		checkReq.setToken(token);
		checkReq.setEncryptedUsername(encUsername);
		String authCheckRequest = checkReq.getJsonString();
		String authResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authResponse);
		
		if(checkResponse.getStatus().equals("Y")) 
		{
			//First decrypt directory, filename
			filename = Dzj_Encrypt_Decrypt.decrypt(filename, checkResponse.getKey1());
			directory = Dzj_Encrypt_Decrypt.decrypt(directory, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Sankalp\\Desktop\\"+directory+filename;
			String fcontent="";
			try 
			{
				fcontent = new String(Files.readAllBytes(Paths.get(path)));
				System.out.println(fcontent);
				fcontent = Dzj_Encrypt_Decrypt.encrypt(fcontent, checkResponse.getKey1());
				//Send fileContent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getStatus());
				readResponse.setFilecontent(fcontent);
			} 
			
			catch (NoSuchFileException e) 
			{
				fcontent = "";
				fcontent = Dzj_Encrypt_Decrypt.encrypt(fcontent, checkResponse.getKey1());
				//Send filecontent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getStatus());
				readResponse.setFilecontent(fcontent);
			}
			
			catch(IOException e) 
			{
				readResponse.setAuthstatus(checkResponse.getStatus());
				e.printStackTrace();
			}
		
		}
		
		else 
		{
			readResponse.setAuthstatus(checkResponse.getStatus());
		}
		
		String readResponseJson = readResponse.getJsonString();
		System.out.println(readResponseJson);
		return readResponseJson;
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/writeFile")
	public String writeFile(String input) 
	{
		Dzj_Url_Properties.loadProperties();
		Dzj_Request_WriteFile writeRequest = new Dzj_Request_WriteFile();
		Dzj_Response_WriteFile writeResponse = new Dzj_Response_WriteFile();
		writeRequest = writeRequest.getClassFromJsonString(input);
		String token = writeRequest.getToken();
		String encUsername = writeRequest.getEncryptedUsername();
		String directory = writeRequest.getDirectory();
		String filename = writeRequest.getFilename();
		String filecontent = writeRequest.getFilecontent();
		//Send request to AS to check token
		Dzj_Request_AuthCheck checkReq = new Dzj_Request_AuthCheck();
		Dzj_Response_AuthCheck checkResponse = new Dzj_Response_AuthCheck();
		Dzj_Support_Functions sf = new Dzj_Support_Functions();
		String path = "";
		checkReq.setToken(token);
		checkReq.setEncryptedUsername(encUsername);
		String authCheckRequest = checkReq.getJsonString();
		String authResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authResponse);
		
		if(checkResponse.getStatus().equals("Y")) 
		{
			//First decrypt directory, filename
			filename = Dzj_Encrypt_Decrypt.decrypt(filename, checkResponse.getKey1());
			directory = Dzj_Encrypt_Decrypt.decrypt(directory, checkResponse.getKey1());
			filecontent = Dzj_Encrypt_Decrypt.decrypt(filecontent, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Sankalp\\Desktop\\"+directory+filename;
			try 
			{
				Files.write(Paths.get(path), filecontent.getBytes());
				writeResponse.setAuthstatus(checkResponse.getStatus());
			}
			
			catch(IOException e) 
			{
				writeResponse.setAuthstatus("N");
				e.printStackTrace();
			}
		}
		
		else 
		{
			writeResponse.setAuthstatus("N");
		}
		
		return writeResponse.getJsonString();
	}
	
	 // Created only to test if webserver is up and running
	 
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) 
	{
		return "up and running";
	}

}
