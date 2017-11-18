package network.types;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Formatter;

import javax.crypto.Mac;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;  

/**
 * Created by cheng on 27/1/2017.
 */

public class HttpAuth {

    private String _apiKey ;
    private String _secretKey ;
    private static final String HEAD_1 = "dc";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public HttpAuth(String apiKey,String secretKey){
       _apiKey = apiKey;
       _secretKey = secretKey;
    }
   

    public String toString(HttpParams params,String requestDateTime)
    {
        System.out.println(HEAD_1+":"+_apiKey+":"+base64Encode(generateSignature(params,_secretKey,requestDateTime)));
    	return HEAD_1+":"+_apiKey+":"+base64Encode(generateSignature(params,_secretKey,requestDateTime));
    }
    
    public String generateSignature(HttpParams params,String secretKey,String requestDateTime) {
    	String encryptText;
    	if (params == null) {
    		encryptText = "null"+"\n"+requestDateTime+"\n";
    	} else {
    		encryptText = params.toString()+"\n"+requestDateTime+"\n";
    	}
		try {
			return calculateRFC2104HMAC(encryptText,secretKey);
		} catch (InvalidKeyException e) {
		} catch (SignatureException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
    }
    
    public String base64Encode(String encodedText) {
    	byte[] encodedBytes = Base64.getEncoder().encode(encodedText.getBytes());
    	return new String(encodedBytes);
    }
   
    
    private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public static String calculateRFC2104HMAC(String data, String key)
		throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}

}
