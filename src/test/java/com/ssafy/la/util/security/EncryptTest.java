package com.ssafy.la.util.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.KeyPair;
import java.security.spec.RSAPublicKeySpec;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EncryptTest {
	
	@Autowired
	RSA_2048 rsa_2048;
	
	@Test
	void encryptSuccessTest() {
		KeyPair key = rsa_2048.createKey();
		String publickey = rsa_2048.keyToString(key.getPublic());
		String privatekey = rsa_2048.keyToString(key.getPrivate());
		
		String testWord = "testWord";
		
		String encryptWord = rsa_2048.encrypt(testWord, publickey);
		String decryptWord = rsa_2048.decrypt(encryptWord, privatekey);
//		
		Assertions.assertThat(testWord).isEqualTo(decryptWord);
		
	}

}
