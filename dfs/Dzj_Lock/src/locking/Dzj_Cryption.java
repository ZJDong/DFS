package locking;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


import java.util.Base64;


// Encrypting the String
public class Dzj_Cryption 
{

	public static String encrypt(String strClearText,String strKey) 
	{
		String enc="";

		try 
		{
			
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			enc=new String(Base64.getEncoder().encodeToString(cipher.doFinal(strClearText.getBytes("Cp1252"))));
			return enc;
		
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchPaddingException e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidKeyException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalBlockSizeException e) 
		{
			e.printStackTrace();
		} 
		catch (BadPaddingException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enc;
		
	}

	// Decrypting the encrypted String
	public static String decrypt(String strEncrypted,String strKey) throws UnsupportedEncodingException 
	{
		String decrypt_str="";

		try 
		{
			byte[] bytEncrypted = Base64.getDecoder().decode(strEncrypted.getBytes("Cp1252"));
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(bytEncrypted);
			decrypt_str=new String(decrypted);
			return decrypt_str;

		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchPaddingException e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidKeyException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalBlockSizeException e) 
		{
			e.printStackTrace();
		} 
		catch (BadPaddingException e) 
		{
			e.printStackTrace();
		}
		return decrypt_str;
	}
	
	
	public static String getInitialKey()  
	{
		Key initlKey=null;
	
		try 
		{
			initlKey = KeyGenerator.getInstance("Blowfish").generateKey();
			
		}
		catch(NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return initlKey.getEncoded().toString();
	}
}
	


