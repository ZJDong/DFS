package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Request_AuthCheck 
{
	String token;
	String encryptedUsername;
	
	
	

	public String getEncryptedUsername() {
		return encryptedUsername;
	}

	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}

	public String getToken() 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}


	
	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
}
