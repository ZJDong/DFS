package directoryService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Request_AuthCheck {

	String token;
	String encryptedUsername;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the encryptedUsername
	 */
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	/**
	 * @param encryptedUsername the encryptedUsername to set
	 */
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
}
