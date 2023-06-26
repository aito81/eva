package py.com.tipcsa.eva.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Crypto {
	
	public static String encriptarPass(String password) {
		String encryptedPass = null;

		try {
			byte[] bytesOfMessage = password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] theDigest = md.digest(bytesOfMessage);
			// encryptedPass = theDigest.toString();
			BigInteger bigInt = new BigInteger(1, theDigest);
			encryptedPass = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (encryptedPass.length() < 32) {
				encryptedPass = "0" + encryptedPass;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return encryptedPass;
	}

}
