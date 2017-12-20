package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_Login 
{	
	String user_type;
	String key1;
	String name;
	String token;
	String status;
	
	

	// Converting to Json String format	
	
	public String getUser_type() {
		return user_type;
	}



	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}



	public String getKey1() {
		return key1;
	}



	public void setKey1(String key1) {
		this.key1 = key1;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String response_login = gson.toJson(this);
		return response_login;
	}
}
