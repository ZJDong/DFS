package directoryService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Dzj_FileInfo_DSRequest {

	String encryptedUsername;
	String token;
	String filename;
	String operation;

	
	
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



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}



	public Dzj_FileInfo_DSRequest getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_FileInfo_DSRequest getFileInfoFromDSRequest = gson.fromJson(replyInString, Dzj_FileInfo_DSRequest.class);
		return getFileInfoFromDSRequest;
	}

}