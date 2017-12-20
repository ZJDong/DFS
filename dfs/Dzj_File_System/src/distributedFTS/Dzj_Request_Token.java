package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Request_Token 
{
	String encryptedUsername;
	String token;
	

	// Getter Setter Methods
	


	public String getEncryptedUsername() {
		return encryptedUsername;
	}


	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	
	
	// Converting from Json String to Class 
	
	public Dzj_Request_Token getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Request_Token request_token = gson.fromJson(param,Dzj_Request_Token.class);
		return request_token;
		
	}

}
