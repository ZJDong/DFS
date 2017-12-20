package directoryService;

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

public class Dzj_EncryptDecrypt 
{

	public static String encrypt(String strClearText,String strKey) 
	{
		String strDatas="";
		
		try 
		{
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			strDatas=new String(Base64.getEncoder().encodeToString(cipher.doFinal(strClearText.getBytes("Cp1252"))));
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
			e.printStackTrace();
		}
		
		return strDatas;
	}

	public static String decrypt(String strEncrypted,String strKey) throws UnsupportedEncodingException 
	{
		String strDatas="";
		try 
		{
			byte[] bytEncrypted = Base64.getDecoder().decode(strEncrypted.getBytes("Cp1252"));
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(bytEncrypted);
			strDatas=new String(decrypted);

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
		
		return strDatas;
	}
	
	public static String getNewKey()  
	{
		Key Key=null;
		try 
		{
			Key = KeyGenerator.getInstance("Blowfish").generateKey();
		}
		
		catch(NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return Key.getEncoded().toString();
	}
	
}

