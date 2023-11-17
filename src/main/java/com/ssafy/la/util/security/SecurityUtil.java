package com.ssafy.la.util.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
	private static HashMap<String, KeyPair> keyMap;
	
	private static SecurityUtil instance = null;
	
	private SecurityUtil() {}
	
	public static SecurityUtil getInstnace() {
		if(instance == null) {
			instance = new SecurityUtil();
			keyMap = new HashMap<>();
		}
		
		return instance;
	}

	public void createKeyPair(String uuid) {
		if(uuid == null)
			return;
		
		if(keyMap.get(uuid) == null) {
			KeyPair keyPair = RSA_2048.createKey();
			keyMap.put(uuid, keyPair);
		}
	}

	public PrivateKey getPrivateKey(String uuid) {
		if(uuid == null)
			return null;
		
		if(keyMap.get(uuid) == null) {
			return null;
		}
		return keyMap.get(uuid).getPrivate();
	}

	public PublicKey getPublicKey(String uuid) {
		if(uuid == null)
			return null;
		
		if(keyMap.get(uuid) == null) {
			return null;
		}
		return keyMap.get(uuid).getPublic();
	}

	public RSAPublicKeySpec getPublicKeySpecFromSecurityUtil(String uuid) {
		if(uuid == null)
			return null;
		
		if(keyMap.get(uuid) == null) {
			return null;
		}
		
		return getPublicKeySpec(keyMap.get(uuid).getPublic());
	}
	
	private static RSAPublicKeySpec getPublicKeySpec(PublicKey pk) {
		if(pk == null)
			return null;
		
		try {
			RSAPublicKeySpec publicSpec = KeyFactory.getInstance("RSA").getKeySpec(pk, RSAPublicKeySpec.class);
			return publicSpec;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPublicKeyModulus(String uuid) {
		RSAPublicKey pk = (RSAPublicKey) instance.getPublicKey(uuid);
		if(pk == null)
			return null;
		
		return pk.getModulus().toString(16);
	}

	public String getPublicKeyExponent(String uuid) {
		RSAPublicKey pk = (RSAPublicKey) instance.getPublicKey(uuid);
		if(pk == null)
			return null;
		return pk.getPublicExponent().toString(16);
	}

}
