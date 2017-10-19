package com.alqsoft.utils.gzyl;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA工具类
 * 
 * @author wangcong
 * @data 2016年3月18日下午5:14:05
 */
public abstract class RsaUtils {
	/**
	 * 生成公钥私钥对，使用默认模长1024。
	 * 
	 * @return Object[] : 0:公钥( RSAPublicKey )，1:私钥( RSAPrivateKey )
	 */
	public static Map<String, String> genKeyPair() {
		return genKeyPair(1024);

	}

	/**
	 * 指定模长生成公私钥对
	 * 
	 * @param modulus
	 * @return
	 */
	public static Map<String, String> genKeyPair(int modulus) {
		KeyPairGenerator keyPairGen;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(modulus);
			KeyPair keyPair = keyPairGen.generateKeyPair();

			Map<String, String> keyMaps = new HashMap<String, String>();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			keyMaps.put("publicKey", new String(Base64.encode(publicKey.getEncoded())));
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMaps.put("privateKey", new String(Base64.encode(privateKey.getEncoded())));

			return keyMaps;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param publicKeyBytes
	 * @param data
	 * @param modulus
	 *            公钥模长，范围512-2048。
	 * @return
	 */
	public static byte[] encryptByPublicKey(byte[] publicKeyBytes, byte[] data, int modulus) {
		try {
			// RSA最大加密明文大小
			int maxEncryptBlock = modulus / 8 - 11;

			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxEncryptBlock) {
					cache = cipher.doFinal(data, offSet, maxEncryptBlock);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxEncryptBlock;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 公钥加密，密钥模长使用默认长度1024。
	 * 
	 * @param publicKeyBytes
	 *            公钥RSAPublicKey getEncoded()
	 * @param data
	 *            要加密的字节数组
	 */
	public static byte[] encryptByPublicKey(byte[] publicKeyBytes, byte[] data) {
		return encryptByPublicKey(publicKeyBytes, data, 1024);
	}

	/**
	 * 公钥解密
	 * 
	 * @param publicKeyBytes
	 *            公钥RSAPublicKey getEncoded()
	 * @param encryptedData
	 *            被(私钥)加密过的字节数组
	 * @param modulus
	 *            模长，范围512-2048
	 * @return
	 */
	public static byte[] decryptByPublicKey(byte[] publicKeyBytes, byte[] encryptedData, int modulus) {
		// RSA最大解密密文大小
		int maxDecryptBlock = modulus / 8;
		try {
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxDecryptBlock) {
					cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxDecryptBlock;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 公钥解密，默认模长1024
	 * 
	 * @param publicKeyBytes
	 *            公钥RSAPublicKey getEncoded()
	 * @param encryptedData
	 *            被(私钥)加密过的字节数组
	 */
	public static byte[] decryptByPublicKey(byte[] publicKeyBytes, byte[] encryptedData) {
		return decryptByPublicKey(publicKeyBytes, encryptedData, 1024);
	}

	/**
	 * 私钥加密
	 * 
	 * @param privateKeyBytes
	 *            私钥RSAPrivateKey getEncoded()
	 * @param data
	 *            要加密的字节数组
	 * @param modulus
	 *            模长，范围512-2048。
	 */
	public static byte[] encryptByPrivateKey(byte[] privateKeyBytes, byte[] data, int modulus) {
		try {
			// RSA最大加密明文大小
			int maxEncryptBlock = modulus / 8 - 11;

			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxEncryptBlock) {
					cache = cipher.doFinal(data, offSet, maxEncryptBlock);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxEncryptBlock;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 私钥加密，默认模长1024。
	 * 
	 * @param privateKeyBytes
	 *            私钥RSAPrivateKey getEncoded()
	 * @param data
	 *            要加密的字节数组
	 */
	public static byte[] encryptByPrivateKey(byte[] privateKeyBytes, byte[] data) {
		return encryptByPrivateKey(privateKeyBytes, data, 1024);
	}

	/**
	 * 私钥解密
	 * 
	 * @param privateKeyBytes
	 *            私钥RSAPrivateKey getEncoded()
	 * @param encryptedData
	 *            被(公钥)加密过的字节数组
	 * @param modulus
	 *            模长，范围512-2048
	 */
	public static byte[] decryptByPrivateKey(byte[] privateKeyBytes, byte[] encryptedData, int modulus) {
		try {
			// RSA最大解密密文大小
			int maxDecryptBlock = modulus / 8;

			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxDecryptBlock) {
					cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxDecryptBlock;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 私钥解密，默认模长1024。
	 * 
	 * @param privateKeyBytes
	 *            私钥RSAPrivateKey getEncoded()
	 * @param encryptedData
	 *            被(公钥)加密过的字节数组
	 */
	public static byte[] decryptByPrivateKey(byte[] privateKeyBytes, byte[] encryptedData) {
		return decryptByPrivateKey(privateKeyBytes, encryptedData, 1024);
	}
}
