package com.hzjytech.operation.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES3Util {
	
	
	
	  private static String PASSWORD_CRYPT_KEY = "F8WoCaONi88";
	  private static String IPS = "fsDaB12s";
	  
	  /**
	   * 加密
	   */
	  public static String encrypt(String message)
	    throws Exception
	  {
	    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	    DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY
	      .getBytes("UTF-8"));
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	    IvParameterSpec iv = new IvParameterSpec(IPS.getBytes("UTF-8"));
	    cipher.init(1, secretKey, iv);
	    byte[] b = cipher.doFinal(message.getBytes("UTF-8"));
	    return Base64.encodeToString(b, 0);
	  }

	  /**
	   * 解密
	   */
	  public static String decrypt(String message)
	    throws Exception
	  {
	    byte[] bytesrc = Base64.decode(message, 0);
	    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	    DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY
	      .getBytes("UTF-8"));
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	    IvParameterSpec iv = new IvParameterSpec(IPS.getBytes("UTF-8"));

	    cipher.init(2, secretKey, iv);

	    byte[] retByte = cipher.doFinal(bytesrc);
	    return new String(retByte);
	  }
	}
