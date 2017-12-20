package locking;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;





public class Dzj_Connecting_Auth_Server {

	public String connection(String url, String input,String type) {
		
		String output="";
		String reply="";
		
		try 
		
		{
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			
			System.out.println("*******\nSending "+type+" Request:\n"+input+"\n*********");
			System.out.println("*******\nSending "+type+" Request to "+url+"\n**********");
			
			post.addHeader("accept", "application/json");
			
			StringEntity se = new StringEntity(input.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while ((output = bufferedReader.readLine()) != null) {
				reply+=output;
			}
			System.out.println("*******\nGot  "+type+" Response:\n"+reply+"\n*********");

		}
		catch(MalformedURLException e) 
		{
			e.printStackTrace();

		}
		catch(IOException e) 
		
		{
			e.printStackTrace();
		}
		
		return reply;
	}


	public String sendAuthRequest(String input) 
	{
	
		String server_ip = Dzj_AuthServer_Properties.Url_IP + Dzj_AuthServer_Properties.Url_path;
		String type = "AuthRequest";
		return connection(server_ip,input,type);

	}
	

}
