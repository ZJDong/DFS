package clientserver;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clientserver.Dzj_Encrypt_Decrypt;
import clientserver.Dzj_FileInfo;
import clientserver.Dzj_FileInfo_Response;
import clientserver.Dzj_Request_Lock;
import clientserver.Dzj_Response_Lock;
import clientserver.Dzj_Request_Read;
import clientserver.Dzj_Response_Read;
import clientserver.Dzj_Support_Functions;

public class Dzj_Write_RemoteFile extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Dzj_Support_Functions sf; 
 
    public Dzj_Write_RemoteFile() 
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		//Get file info from Directory Service
		Dzj_FileInfo infoRequest = new Dzj_FileInfo();
		infoRequest.setFilename(Dzj_Encrypt_Decrypt.encrypt(filename, key1));
		infoRequest.setToken(token);
		infoRequest.setEncryptedUsername(usernameEnc);
		infoRequest.setOperation("w");
		String infoRequestJson = infoRequest.getJsonString();
		String replyInfoRequest = sf.getDirectoryInfo(infoRequestJson);
		Dzj_FileInfo_Response fileInfoResponseBack = new Dzj_FileInfo_Response();
		fileInfoResponseBack=fileInfoResponseBack.getClassFromJsonString(replyInfoRequest);
		
		if(fileInfoResponseBack.getAuthstatus().equals("Y"))
		{
			fileInfoResponseBack.setServerurl(Dzj_Encrypt_Decrypt.decrypt(fileInfoResponseBack.getServerurl(),key1));
			Dzj_Request_Read readRequest = new Dzj_Request_Read();
			readRequest.setFilename(Dzj_Encrypt_Decrypt.encrypt(filename, key1));
			readRequest.setToken(token);
			readRequest.setEncryptedUsername(usernameEnc);
			readRequest.setDirectory(fileInfoResponseBack.getDirectory());//This is also encrypted with key1
			String jsonReadRequest = readRequest.getJsonString();
			String readResponsereply = sf.sendReadRequest(jsonReadRequest,fileInfoResponseBack.getServerurl());
			Dzj_Response_Read readResponse = new Dzj_Response_Read();
			readResponse = readResponse.getClassFromJsonString(readResponsereply);
			String filecontent = Dzj_Encrypt_Decrypt.decrypt(readResponse.getFilecontent(),key1);
			
			//Send Request to lock file
			Dzj_Request_Lock lockRequest = new Dzj_Request_Lock();
			lockRequest.setEmail(Dzj_Encrypt_Decrypt.encrypt("test@gmail.com", key1));
			lockRequest.setFilename(Dzj_Encrypt_Decrypt.encrypt(filename, key1));
			lockRequest.setToken(token);
			lockRequest.setUsername(usernameEnc);
			String lockRequestStr = lockRequest.getJsonString();
			String lockResponseStr = sf.sendLockRequest(lockRequestStr);
			Dzj_Response_Lock lockResponse = new Dzj_Response_Lock(); 
			lockResponse = lockResponse.getClassFromJsonString(lockResponseStr);
			
			//Success case 
			if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("Y"))	
			{
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				request.getSession().setAttribute("directory", Dzj_Encrypt_Decrypt.decrypt(fileInfoResponseBack.getDirectory(),key1));
				request.getSession().setAttribute("serverurl", fileInfoResponseBack.getServerurl());
				System.out.println(filecontent);
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}
			
			//Failure case with unable to get lock on file
			else if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("N")) 
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponseBack.getAuthstatus()==null)
				    fileInfoResponseBack.setAuthstatus("");
				request.getSession().setAttribute("message", "Unable to get lock on file, file is already locked");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			} 
			
			//Failure case when token validation or any other problem
			else
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponseBack.getAuthstatus()==null)
					fileInfoResponseBack.setAuthstatus("");
				request.getSession().setAttribute("message", "Token Validation Failed");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}			
		}
		
		else 
		{
			System.out.println("Validation Failed");
			request.getSession().setAttribute("status", "0");
			if(fileInfoResponseBack.getAuthstatus()==null)
				fileInfoResponseBack.setAuthstatus("");
			request.getSession().setAttribute("message", fileInfoResponseBack.getAuthstatus());
			request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
