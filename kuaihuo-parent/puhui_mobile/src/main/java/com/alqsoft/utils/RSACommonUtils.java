package com.alqsoft.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.alqframework.security.RSAUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sunhuijie
 *
 * @date 2017年3月30日
 *
 */
public class RSACommonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(RSACommonUtils.class);

	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRb/pV/isJqzv9VgylHuDA1H7b6pXkeQylUUn3RwIkM3whgUO+ysYPETRQGSDKPJ6DPdgBJO/xsQyV1DUFuPg1sdtJPnhSfU82E+soQJ9AUZ8RUHpbyckZJ+JSndb3OVKfzB1YGFc3LpOINi1QlKuvCtJJ/Se7HhuNMXUPxeEhrQIDAQAB";

	private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJFv+lX+KwmrO/1WDKUe4MDUftvqleR5DKVRSfdHAiQzfCGBQ77Kxg8RNFAZIMo8noM92AEk7/GxDJXUNQW4+DWx20k+eFJ9TzYT6yhAn0BRnxFQelvJyRkn4lKd1vc5Up/MHVgYVzcuk4g2LVCUq68K0kn9J7seG40xdQ/F4SGtAgMBAAECgYEAg6ZVeRoGY045k20dLSryUBsKao/C93oL1GntBoWkjph8OOgGJuLuvhzYudjFMcwFwM9uhDqnaKTXu5jIFlIFe08GsxV0bABHRP9mqyM2Ux/2hB6hOtdD8D4n7CVlQxAISYhGzjbX/wRsefyMY093cChRtbsQpKaELJSRDOxt/wECQQDyQA3Fv6zKQlAkbxdogmNo/hf3jCOL4XIZOJEEsCoepZ7d/H1M2iGw/xKSlYpsZva0/hcO6R8w89g2LVVZdKbxAkEAmbE2wjQ/fkBvOBYgt2EaeCXrQeGPmSt5RY2GO0MbhBenSmeqmDsRvjme+Zy2GuV98R6NFQ38VV/Tqy5zk9F+fQJAEo8/vt/BgDl2ZGQyeepLbXbSJUASVRwvnqkLyz0n7PMpab53ZxTrMeFI7fwQ/98flZxF7fmETz7PWM8+U70xMQJALnAnVYKn9KPPdyjodcA3WTpL3TokQ2mpWOSsPFfIhp8HAypTl2+xqsKKUG2TcIkLBsHuawAw0DMGJ+D/NxjDmQJAXzwlDp7u0OIJIXrRkV+YaaKRsw61I+HwJBO0bMbydjLz9uCZYaTTsfi0fgNRUXK1UZrbr82coeqFlhLg083gRQ==";
	
	public static enum CharSet {
		UTF8 {
			public String getName() {
				return "UTF-8";
			}
		};
		public abstract String getName();
	}
	
	/**
	 * 创建公钥  私钥
	 * @return
	 */
	public static Map<String, Object> createRSAKey() {
		Map<String, Object> keyMap = null;
		try {
			keyMap = RSAUtils.genKeyPair();
			logger.info("公钥-->"+RSAUtils.getPublicKey(keyMap));
			logger.info("私钥-->"+RSAUtils.getPrivateKey(keyMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取公私钥失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 根据公钥加密
	 * @param content 需加密内容
	 * @param charSet 字符集编码格式
	 * @return
	 */
	public static String encryptByPublicKey(String content, CharSet charSet) {
		byte[] b=null;
		try {
			b = encrypt(content.getBytes(charSet.getName()),true);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return bytesToString(b);
	}
	
	/**
	 * 根据私钥加密
	 * @param content
	 * @param charSet
	 * @return
	 */
	public static String encryptByPrivateKey(String content, CharSet charSet) {
		byte[] b=null;
		try {
			b = encrypt(content.getBytes(charSet.getName()),false);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return bytesToString(b);
	}

	/**
	 * 根据私钥解密字符串
	 * @param content  学解密内容
	 * @param charSet  字符集编码格式
	 * @return
	 */
	public  static String decryptByPrivateKey(String content, CharSet charSet) {
 
		byte[] b = decrypt(stringToBytes(content),false);
		try {
			return new String(b, charSet.getName());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据公钥解密字符串
	 * @param content
	 * @param charSet
	 * @return
	 */
	public  static String decryptByPublicKey(String content, CharSet charSet) {
		 
		byte[] b = decrypt(stringToBytes(content),true);
		try {
			return new String(b, charSet.getName());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("不支持的编码格式，错误信息"+e.getMessage());
		}
		return null;
	}

	/**
	 * 字符串转换成为字节数组
	 * @param decrytString
	 * @return
	 */
	private  static byte[] stringToBytes(String decrytString) {
		 
		char[] charHex=decrytString.toCharArray();
		byte[] clone=null;
		try {
			clone = Hex.decodeHex(charHex);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			logger.info("字符串转换字节数组失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}		
		return clone;
	}

	/**
	 * 字节数组转换为字符串
	 * @param decrytString
	 * @return
	 */
	private  static String bytesToString(byte[] encrytpByte) {
		char[] charHex=Hex.encodeHex(encrytpByte);
		return new String(charHex);
	}

	/**
	 * 加密
	 * @param data
	 * @return
	 */
	private static byte[] encrypt(byte[] data,boolean isPublicKey) {
		try {
			
			if(isPublicKey){
				return RSAUtils.encryptByPublicKey(data, publicKey);
			}
			return RSAUtils.encryptByPrivateKey(data, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("加密失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param data
	 * @return
	 */
	private static byte[] decrypt(byte[] data,boolean isPublicKey) {
		 
		try {
			if(isPublicKey){
				return RSAUtils.decryptByPublicKey(data, publicKey);
			}
			return RSAUtils.decryptByPrivateKey(data, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("私钥解密失败，错误信息"+e.getMessage());
			e.printStackTrace();
		}
		 
		return null;
	}
	


}
