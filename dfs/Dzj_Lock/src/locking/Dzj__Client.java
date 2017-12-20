package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj__Client {

	String lockstatus;
	String authstatus;
	String releasestatus; 
	
	


	public String getLockstatus() {
		return lockstatus;
	}



	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}



	public String getAuthstatus() {
		return authstatus;
	}



	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}



	public String getReleasestatus() {
		return releasestatus;
	}



	public void setReleasestatus(String releasestatus) {
		this.releasestatus = releasestatus;
	}



	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String responsetoclient = gson.toJson(this);
		return responsetoclient;
	}
}
