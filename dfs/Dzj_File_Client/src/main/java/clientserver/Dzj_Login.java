package clientserver;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientserver.Dzj_Encrypt_Decrypt;

import clientserver.Dzj_Request_Login;
import clientserver.Dzj_Response_Login;
import clientserver.Dzj_Url_Property;
import clientserver.Dzj_Support_Functions;


// Servlet implementation class Login
 
public class Dzj_Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Dzj_Support_Functions sf;
       
    // @see HttpServlet#HttpServlet()
     
    public Dzj_Login() 
    {
        super(); // Constructor
    }

	//@see Servlet#init(ServletConfig)
	
	public void init(ServletConfig config) throws ServletException 
	{
		sf = new Dzj_Support_Functions();
		Dzj_Url_Property.import_Properties();
	}

	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Dzj_Request_Login loginRequest= new Dzj_Request_Login();
		loginRequest.setUsername(Dzj_Encrypt_Decrypt.encrypt((String) request.getParameter("u"), (String) request.getParameter("p")));
		loginRequest.setPassword(Dzj_Encrypt_Decrypt.encrypt((String) request.getParameter("p"), (String) request.getParameter("p")));
		String reply = sf.sendLoginRequest(loginRequest.getJsonString());
		Dzj_Response_Login lResponse = new Dzj_Response_Login();
		lResponse = lResponse.getClassFromJsonString(reply);
		
		if(lResponse.getStatus().equals("Y")&&lResponse.getUser_type().equals("N")) 
		{
			request.getSession().setAttribute("fname", lResponse.getName());
			request.getSession().setAttribute("token", lResponse.getToken());
			request.getSession().setAttribute("key1", lResponse.getKey1());
			request.getSession().setAttribute("usernamenc", loginRequest.getUsername());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
			request.getSession().setAttribute("message", "");
			request.getSession().setAttribute("status", "");
		}
		
		else 
		{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

	
	 // @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
