package com.djy.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Base64Encryption {

	private final static String HEX = "0123456789EFGHIJ";
	//这里IV和myKey必须是8位，超过或者少于8位会报错
	private final static byte[] IV = {6,4,3,9,9,6,2,2};
	private static String myKey = "zbhpoint";



	public static String encrypt(String prePassword, String secret)
			throws Exception {
		SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

		byte[] results = cipher.doFinal(prePassword.getBytes());
		return Base64.encodeBase64String(results);
	}



	public static String decrypt(String encStr, String secret) throws Exception {
		SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));

		byte[] result = cipher.doFinal(Base64.decodeBase64(encStr));
		return new String(result);
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuilder result = new StringBuilder(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			result.append(HEX.charAt((buf[i] >> 4) & 0x0f)).append(
					HEX.charAt(buf[i] & 0x0f));
		}
		return result.toString();
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

    /*加密过程*/
	public static String encrypt(String plainText) {
		try {
			String enKey = encrypt(plainText, myKey);
			// 为了防止加密后出现特殊字符，比如"+",参数是传递不过去的
			enKey = URLEncoder.encode(enKey, "utf-8");
			return enKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /*解密过程*/
	public static String decrypt(String encryptedText) {
		try {
			if (encryptedText.indexOf("%") != -1) {
				encryptedText = URLDecoder.decode(encryptedText, "utf-8");
			}
			return decrypt(encryptedText, myKey);
		} catch (Exception e) {
			return "false";
		}
	}


	public static void main(String[] args) {
		String str = encrypt("1zbh7575");
		System.out.println(str);
		System.out.println(decrypt(str));
	}
}
