package com.ph.shopping.common.util.rsa.bj;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName:  RSACommonUtils   
 * @Description:rsa 加密工具类（针对北京方使用）   
 * @author: 李杰
 * @date:   2017年7月10日 下午2:16:25     
 * @Copyright: 2017
 */
public class BJRSACommonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BJRSACommonUtils.class);
	/**
	 * 公钥
	 */
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiahhmGlsiwtIgRNX0HgHDeqsmCPtGqZ3fiig5TLv9Qqj/On4yCSk/xx+I2esiNT1z0WhWqTnN9UbbyfmmFpqhXcpC8PyLXrbPcK/F7jldGreXtiTfBcBFyIQ33rCL7AE4mrcYnz7yYc036db2fzenWDlAR7srJUQ8xFZ6ZzVRZwIDAQAB";
	/**
	 * 
	 * @ClassName:  CharSet   
	 * @Description:字符集枚举   
	 * @author: 李杰
	 * @date:   2017年7月10日 下午2:17:11     
	 * @Copyright: 2017
	 */
	public static enum CharSet {
		UTF8 {
			public String getName() {
				return "UTF-8";
			}
		};
		public abstract String getName();
	}
	/**
	 * 根据公钥加密
	 * @param content 需加密内容
	 * @param charSet 字符集编码格式
	 * @return
	 */
	public static String encryptByPublicKey(String content, CharSet charSet) {
		byte[] b = null;
		try {
			b = encrypt(content.getBytes(charSet.getName()));
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的编码格式，错误信息", e);
		}
		return bytesToString(b);
	}
	

	/**
	 * 字符串转换成为字节数组
	 * @param decrytString
	 * @return
	 */
	public static byte[] stringToBytes(String decrytString) {
		char[] charHex = decrytString.toCharArray();
		byte[] clone = null;
		try {
			clone = Hex.decodeHex(charHex);
		} catch (DecoderException e) {
			logger.info("字符串转换字节数组失败，错误信息", e);
		}
		return clone;
	}

	/**
	 * 字节数组转换为字符串
	 * @param decrytString
	 * @return
	 */
	private static String bytesToString(byte[] encrytpByte) {
		char[] charHex = Hex.encodeHex(encrytpByte);
		return new String(charHex);
	}
	/**
	 * 加密
	 * @param data
	 * @return
	 */
	private static byte[] encrypt(byte[] data) {
		try {
			return BJRSAUtils.encryptByPublicKey(data, publicKey);
		} catch (Exception e) {
			logger.info("加密失败，错误信息", e);
		}
		return null;
	}

}
