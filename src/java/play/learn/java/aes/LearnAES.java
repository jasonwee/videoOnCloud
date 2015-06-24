package play.learn.java.aes;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class LearnAES {
	
	public static byte[] decrypt(Key secKey, byte[] cipherText) throws Exception {
		Cipher aesCipher = Cipher.getInstance("AES");
		
		aesCipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] bytePlainText = aesCipher.doFinal(cipherText);
		
		return bytePlainText;
	}
	
	public static byte[] encrypt(Key secKey) throws Exception {
		Cipher aesCipher = Cipher.getInstance("AES");
		
		byte[] byteText = "Your Plain Text Here".getBytes();
		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
		byte[] byteCipherText = aesCipher.doFinal(byteText);
		return byteCipherText;
	}

	public static void main(String[] args) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		
		SecretKey myLittleSecretKey = keyGen.generateKey();
		byte[] transferMe = LearnAES.encrypt(myLittleSecretKey);
		
		
		System.out.println(new String(LearnAES.decrypt(myLittleSecretKey, transferMe)));
	}

}
