package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_Read 
{
	String filecontent;

	public String getFilecontent() 
	{
		return filecontent;
	}

	public void setFilecontent(String filecontent) 
	{
		this.filecontent = filecontent;
	}
	
	public Dzj_Response_Read getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Response_Read response = gson.fromJson(replyInString, Dzj_Response_Read.class);
		return response;
	}
}
