package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Dzj_Request_Lock {

	String username;
	String filename;
	String token;
	String email;
	
	
	
	/**
	 * @return the uname
	 */
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}
	/**
	 * @input filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @input token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @input email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Dzj_Request_Lock getClassFromJson(String input)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Request_Lock lrequest = gson.fromJson(input,Dzj_Request_Lock.class);
		return lrequest;
		
	}


}
