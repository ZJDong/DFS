package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Dzj_Response_AuthCheck 
{
	String key1;
	String status;

	// Getter Setter Methods




	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getKey1() 
	{
		return key1;
	}


	public void setKey1(String key1) 
	{
		this.key1 = key1;
	}


	public Dzj_Response_AuthCheck getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Response_AuthCheck response = gson.fromJson(replyInString, Dzj_Response_AuthCheck.class);
		return response;
	}
}
