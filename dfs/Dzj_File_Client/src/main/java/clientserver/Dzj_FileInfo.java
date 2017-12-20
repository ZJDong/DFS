package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_FileInfo 
{

	String token;
	String filename;
	String encryptedUsername;
	String operation;
	
	
	
	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public String getEncryptedUsername() {
		return encryptedUsername;
	}



	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}



	//Convert class to Json String
	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}

}
