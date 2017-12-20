package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_ReadFile 
{

	public String authstatus;
	public String filecontent;
	
	
	
	
	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Dzj_Response_ReadFile getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Response_ReadFile response = gson.fromJson(replyInString, Dzj_Response_ReadFile.class);
		return response;
	}
}
