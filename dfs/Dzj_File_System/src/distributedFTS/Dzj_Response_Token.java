package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Response_Token {
	String status;
	String key1;

	
	// Getter Setter Methods
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

	// Converting to Json String
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String response_token = gson.toJson(this);
		return response_token;
	}

}
