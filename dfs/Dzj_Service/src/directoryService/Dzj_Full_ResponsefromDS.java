package directoryService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Dzj_Full_ResponsefromDS 
{

	String filenameArray;
	String directoryArray;
	

	public String getFilenameArray() 
	{
		return filenameArray;
	}


	public void setFilenameArray(String filenameArray) 
	{
		this.filenameArray = filenameArray;
	}


	public String getDirectoryArray() 
	{
		return directoryArray;
	}


	public void setDirectoryArray(String directoryArray) 
	{
		this.directoryArray = directoryArray;
	}


	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
}