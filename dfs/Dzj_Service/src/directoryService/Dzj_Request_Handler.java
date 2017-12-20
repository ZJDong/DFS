package directoryService;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import directoryService.Dzj_EncryptDecrypt;
import directoryService.Dzj_Support_Functions;

@Path("/dirServiceSearch")
public class Dzj_Request_Handler 
{
	@POST
	@Consumes({"application/json"})
	@Path("/fetchFileInfo")
	public String getFileInfo(String input) 
	{
		Dzj_DatabaseProperties.loadProperties();
		Dzj_FileInfo_DSResponse getFileInfoFromDSResponse = new Dzj_FileInfo_DSResponse();
		Dzj_FileInfo_DSRequest getFileInfoFromDSRequest = new Dzj_FileInfo_DSRequest();
		String getFileInfoString=new String();
		Dzj_Support_Functions sf = new Dzj_Support_Functions();
		Dzj_Request_AuthCheck checkReq = new Dzj_Request_AuthCheck();
		Dzj_Response_AuthCheck checkResponse = new Dzj_Response_AuthCheck();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();
		
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		
		if(checkResponse.getStatus().equals("Y")) 
		{			
				try 
				{
					if(getFileInfoFromDSRequest.getOperation().equals("r"))
					fileStats = sf.getFileLocationForRead(Dzj_EncryptDecrypt.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
					else
					fileStats = sf.getFileLocationForWrite(Dzj_EncryptDecrypt.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
					
					if(fileStats.isEmpty()) 
					{
						getFileInfoFromDSResponse.setAuthstatus("Directory Information Not Available for the file"); 
						getFileInfoString = getFileInfoFromDSResponse.getJsonString();
						return getFileInfoString;
					}
				
				} 
				
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
			
				getFileInfoFromDSResponse.setServerurl(Dzj_EncryptDecrypt.encrypt(fileStats.get("serverurl"),checkResponse.getKey1()));
				getFileInfoFromDSResponse.setDirectory(Dzj_EncryptDecrypt.encrypt(fileStats.get("directory"),checkResponse.getKey1()));
				getFileInfoFromDSResponse.setAuthstatus("Y");
			
		}
		
		else 
		{
			getFileInfoFromDSResponse.setAuthstatus("Validation of token Failed");
		}
		
		getFileInfoString = getFileInfoFromDSResponse.getJsonString();
		System.out.println(getFileInfoString);
		return getFileInfoString;
	}
	
	// Full Info of the directory
	
	@POST
	@Consumes({"application/json"})
	@Path("/fullInfoDir")
	public String getDirInfo(String input) 
	{
		Dzj_DatabaseProperties.loadProperties();
		Dzj_FileInfo_DSRequest getFileInfoFromDSRequest = new Dzj_FileInfo_DSRequest();
		Dzj_Full_ResponsefromDS getResponse = new Dzj_Full_ResponsefromDS();
		String getFileInfoString=new String();
		Dzj_Support_Functions sf = new Dzj_Support_Functions();
		Dzj_Request_AuthCheck checkReq = new Dzj_Request_AuthCheck();
		Dzj_Response_AuthCheck checkResponse = new Dzj_Response_AuthCheck();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		if(checkResponse.getStatus().equals("Y")) 
		{
			HashMap<String, String> fileList = sf.getCompleteFileList();
			getResponse.setFilenameArray(fileList.get("filename"));
			getResponse.setDirectoryArray(fileList.get("directory"));
			return getResponse.getJsonString();
		}
		
		else 
		{
			return "{\"authstatus\":\"N\"}";
		}
	}

	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) 
	{
		return "up and running";
	}
}

