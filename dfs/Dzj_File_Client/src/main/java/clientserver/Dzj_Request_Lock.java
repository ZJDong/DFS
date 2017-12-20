package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Request_Lock {

	public String filename;
	public String username;
	public String token;
	public String email;
	/**
	 * @return the filename
	 */
	public String getFilename() 
	{
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) 
	{
		this.filename = filename;
	}
	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	/**
	 * @return the token
	 */
	public String getToken() 
	{
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) 
	{
		this.token = token;
	}
	/**
	 * @return the email
	 */
	public String getEmail() 
	{
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Dzj_Request_Lock getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Request_Lock request = gson.fromJson(replyInString, Dzj_Request_Lock.class);
		return request;
	}
}
