package clientserver;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

public class Dzj_Encrypt_Decrypt 
{
	public static String encrypt(String strClearText,String strKey) 
	{
		String Encrypted="";
		
		try 
		{
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			Encrypted=new String(Base64.getEncoder().encodeToString(cipher.doFinal(strClearText.getBytes("Cp1252"))));
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
		
		return Encrypted;
	}

	public static String decrypt(String Encrypted,String strKey) throws UnsupportedEncodingException {
		String strDecrypted="";
		try 
		{
			byte[] bytEncrypted = Base64.getDecoder().decode(Encrypted.getBytes("Cp1252"));
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(bytEncrypted);
			strDecrypted=new String(decrypted);

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
		
		return strDecrypted;
	}
	
	public static String getInitialKey()  
	{
		Key initKey=null;
		try 
		{
			initKey = KeyGenerator.getInstance("Blowfish").generateKey();
			
		}
		
		catch(NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return initKey.getEncoded().toString();
	}
	
}

