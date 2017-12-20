package clientserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientserver.Dzj_Encrypt_Decrypt;
import clientserver.Dzj_Caching_Memory;
import clientserver.Dzj_FileInfo;
import clientserver.Dzj_FileInfo_Response;
import clientserver.Dzj_Request_Read;
import clientserver.Dzj_Response_Read;
import clientserver.Dzj_Support_Functions;

//Servlet implementation class ReadRemoteFile

public class Dzj_RemoteFile extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Dzj_Support_Functions sf; 
  
    public Dzj_RemoteFile() 
    {
        super();
    }
    

	public void init(ServletConfig config) throws ServletException 
	{
		sf = new Dzj_Support_Functions();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String encryptedUsername = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		// Verify whether the file is in Cache or not
		
		Long ts_then = Dzj_Caching_Memory.cacheStoreTime.get(filename);
		Long ts_now = System.currentTimeMillis();
		Long ts_max = TimeUnit.MINUTES.toMillis(Long.parseLong(Dzj_Url_Property.cache_Time));
		
		if(ts_then != null && ts_now-ts_then<ts_max) 
		{//Serve from cache if entry time is within persistence limit
			String filecontent = Dzj_Caching_Memory.cacheStore.get(filename);
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("filecontent", filecontent);
			request.getSession().setAttribute("filename", filename);
			request.getRequestDispatcher("readfile.jsp").forward(request, response);
		}
		
		else
		{
			if(ts_then != null && ts_now-ts_then>ts_max)  //Remove from cache if older than max persistence time allowed
			{
				Dzj_Caching_Memory.cacheStore.remove(filename);
				Dzj_Caching_Memory.cacheStoreTime.remove(filename);
			}
		
		
			//Get file info from Directory Service
			Dzj_FileInfo infoRequest = new Dzj_FileInfo();
			infoRequest.setFilename(Dzj_Encrypt_Decrypt.encrypt(filename, key1));
			infoRequest.setToken(token);
			infoRequest.setEncryptedUsername(encryptedUsername);
			infoRequest.setOperation("r");
			String infoRequestJson = infoRequest.getJsonString();
			String replyInfoRequest = sf.getDirectoryInfo(infoRequestJson);
			Dzj_FileInfo_Response fileInfoResponse = new Dzj_FileInfo_Response();
			fileInfoResponse=fileInfoResponse.getClassFromJsonString(replyInfoRequest);
		
			if(fileInfoResponse.getAuthstatus().equals("Y"))
			{
				fileInfoResponse.setServerurl(Dzj_Encrypt_Decrypt.decrypt(fileInfoResponse.getServerurl(),key1));
				Dzj_Request_Read readRequest = new Dzj_Request_Read();
				readRequest.setFilename(Dzj_Encrypt_Decrypt.encrypt(filename, key1));
				readRequest.setToken(token);
				readRequest.setEncryptedUsername(encryptedUsername);
				readRequest.setDirectory(fileInfoResponse.getDirectory());//This is also encrypted with key1
				String jsonReadRequest = readRequest.getJsonString();
				String readResponsereply = sf.sendReadRequest(jsonReadRequest,fileInfoResponse.getServerurl());
				Dzj_Response_Read readResponse = new Dzj_Response_Read();
				readResponse = readResponse.getClassFromJsonString(readResponsereply);
				String filecontent = Dzj_Encrypt_Decrypt.decrypt(readResponse.getFilecontent(),key1);
			
				if(Dzj_Caching_Memory.cacheStore.size()<20) 
				{
					System.out.println("Size of cache: "+ Dzj_Caching_Memory.cacheStore.size());
					Dzj_Caching_Memory.cacheStore.put(filename, filecontent);
					Dzj_Caching_Memory.cacheStoreTime.put(filename, System.currentTimeMillis());
				}
			
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				request.getRequestDispatcher("readfile.jsp").forward(request, response);
			}
		
			else 
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthstatus()==null)
					fileInfoResponse.setAuthstatus("");
				request.getSession().setAttribute("message", fileInfoResponse.getAuthstatus());
				request.getRequestDispatcher("readfile.jsp").forward(request, response);
			}
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
