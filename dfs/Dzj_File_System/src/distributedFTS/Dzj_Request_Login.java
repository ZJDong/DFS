package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Request_Login 
{

	String filename;
	String username;
	String password;
	
   

	// Convert from Json String to Class

	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Dzj_Request_Login getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Request_Login lrequest = gson.fromJson(param,Dzj_Request_Login.class);
		return lrequest;
		
	}
	
}
