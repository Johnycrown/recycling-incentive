package com.payment.remittance.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

@Slf4j
public class MobileSecurityUtil {
	
	public static String key ="1234567812345678";
	
	public static byte[] encrypt2(byte[] data, byte[] key, byte[] ivs) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        byte[] finalIvs = new byte[16];
	        int len = ivs.length > 16 ? 16 : ivs.length;
	        System.arraycopy(ivs, 0, finalIvs, 0, len);
	        IvParameterSpec ivps = new IvParameterSpec(finalIvs);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivps);
	        return cipher.doFinal(data);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	public static byte[] decrypt2(byte[] data, byte[] key, byte[] ivs) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        byte[] finalIvs = new byte[16];
	        int len = ivs.length > 16 ? 16 : ivs.length;
	        System.arraycopy(ivs, 0, finalIvs, 0, len);
	        IvParameterSpec ivps = new IvParameterSpec(finalIvs);
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivps);
	        return cipher.doFinal(data);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	private static final String characterEncoding = "UTF-8";    
    private static final String cipherTransformation = "AES/CBC/PKCS5Padding";    
    private static final String aesEncryptionAlgorithm = "AES"; 



	public static String decrypt(String plainTextString, String SecretKey)
			throws
            GeneralSecurityException,
            IOException {

		Base64 base64 = new Base64();
		byte[] cipheredBytes = base64.decode(plainTextString); //,Base64.NO_OPTIONS);
		byte[] keyBytes = getKeyBytes(SecretKey);
		return new String(decrypt(cipheredBytes, keyBytes, keyBytes),characterEncoding);
	}

	public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key,aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		cipherText = cipher.doFinal(cipherText);
		return cipherText;
	}

	public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key,aesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		plainText = cipher.doFinal(plainText);
		return plainText;
	}

	private static byte[] getKeyBytes(String keyx) throws UnsupportedEncodingException {
		//key = "1234567812345678";
		byte[] keyBytes = new byte[16];
		String keyz = keyx;
		if(keyz == null || keyz.equals(""))
			keyz = key;
		
		byte[] parameterKeyBytes = keyz.getBytes(characterEncoding);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0,
				Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public static String encrypt(String plainText, String key)
			throws UnsupportedEncodingException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		
		byte[] plainTextbytes = plainText.getBytes(characterEncoding);
		byte[] keyBytes = getKeyBytes(key);
		byte[] bbe = encrypt(plainTextbytes, keyBytes, keyBytes);
		Base64 base64 = new Base64();
		return new String(base64.encode(bbe),characterEncoding);
	}
	
	
	public static String Hash512Message(String data)
    {
    	String msg ="";
    	try
    	{
    		
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
	     byte[] hashBytes = data.getBytes(StandardCharsets.UTF_8);
	    byte[] messageDigest = digest.digest(hashBytes);
	     StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < messageDigest.length; i++)
	    {
	        String h = Integer.toHexString(0xFF & messageDigest[i]);
	        while(h.length() < 2)
	            h = "0" + h;
	        sb.append(h);
	    }
	    msg =  sb.toString();
	    	
    	}
    	catch(Exception ex)
    	{
    		log.info("exception thrown {}", ex);
    	}
    	
    	return msg;
    	
    }

	
	public static String generateHMACSHA256(final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
		if (key == null || data == null) throw new NullPointerException();
		final Mac hMacSHA256 = Mac.getInstance("HmacSHA256");
		byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
		final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA256");
		hMacSHA256.init(secretKey);
		byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
		byte[] res = hMacSHA256.doFinal(dataBytes);
		return Base64.encodeBase64String(res);
	}

	public static void main(String[] args)
	{
		try
		{
			String strx = encrypt("72662666FGHHHHH","");
			System.out.println("enc " + strx);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	

    
	

}
