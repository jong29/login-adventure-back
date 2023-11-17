package com.ssafy.la.util.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyPair;
import java.security.spec.RSAPublicKeySpec;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EncryptTest {

	SecurityUtil security = SecurityUtil.getInstnace();
	
	@Test
	void encryptSuccessTest() {
		KeyPair key = RSA_2048.createKey();
		String publickey = RSA_2048.keyToString(key.getPublic());
		String privatekey = RSA_2048.keyToString(key.getPrivate());
		
		String testWord = "testWord";
		
		String encryptWord = RSA_2048.encrypt(testWord, publickey);
		String decryptWord = RSA_2048.decrypt(encryptWord, privatekey);
//		
		Assertions.assertThat(testWord).isEqualTo(decryptWord);
		
	}

}
