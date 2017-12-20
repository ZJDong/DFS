package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_Login {

	String name;
	String token;
	String status;
	String user_type;
	String key1;
	public final String getStatus() {
		return status;
	}
	public final void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
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
	 * @return the usertype
	 */
	public String getUser_type() {
		return user_type;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	/**
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}
	/**
	 * @param key1 the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public Dzj_Response_Login getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Dzj_Response_Login loginResponse = gson.fromJson(replyInString, Dzj_Response_Login.class);
		return loginResponse;
	}
}
