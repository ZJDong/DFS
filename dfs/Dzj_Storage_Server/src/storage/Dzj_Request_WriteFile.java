package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Dzj_Request_WriteFile 
{
	String encryptedUsername;
	String directory;
	String filecontent;
	String filename;
	String token;
	
	

	public String getEncryptedUsername() {
		return encryptedUsername;
	}

	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Dzj_Request_WriteFile getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Request_WriteFile request = gson.fromJson(replyInString, Dzj_Request_WriteFile.class);
		return request;
	}

}
