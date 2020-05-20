package com.sendi.netrequest;

import java.security.MessageDigest;

public class MD5AndSHA {
	
	public final static String MD5 = "MD5";
	public final static String SHA = "SHA";//SHA-256 SHA-512
	public final static String SHA1 = "SHA-1";
	
	public final static String md5(String s){
		return MD5_SHA(s,MD5AndSHA.MD5);
	}
	
	public final static String sha(String s){
		return MD5_SHA(s,MD5AndSHA.SHA);
	}

	public final static String MD5_SHA(String s, String type) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance(type);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

}
