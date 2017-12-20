package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_WriteFile 
{
	String authstatus;

	
	
	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Dzj_Response_WriteFile getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Response_WriteFile response = gson.fromJson(replyInString, Dzj_Response_WriteFile.class);
		return response;
	}
}