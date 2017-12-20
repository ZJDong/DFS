package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Lock 
{
	
	String token;
	String email;
	String filename;
	String username;

	
	public String getToken() 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getFilename() 
	{
		return filename;
	}

	public void setFilename(String filename) 
	{
		this.filename = filename;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public Dzj_Lock getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Lock lockRequest = gson.fromJson(param,Dzj_Lock.class);
		return lockRequest;
		
	}
	
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String responsetoclient = gson.toJson(this);
		return responsetoclient;
	}
}