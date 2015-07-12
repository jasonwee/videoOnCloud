package play.learn.java.java8;

import java.util.Base64;
import java.util.UUID;

public class LearnBase64 {

	public static void main(String[] args) throws Exception {
		
		// encode using basic encoder
		String base64encodedString = Base64.getEncoder().encodeToString("fruit?durian".getBytes("utf-8"));
		System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);

		// Decode
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("Original String: "+new String(base64decodedBytes, "utf-8"));

        base64encodedString = Base64.getUrlEncoder().encodeToString("fruit?durian".getBytes("utf-8"));
        System.out.println("Base64 Encoded String (URL) :" + base64encodedString);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
           stringBuilder.append(UUID.randomUUID().toString());
        }

        byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
        String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
           System.out.println("Base64 Encoded String (MIME) :"+mimeEncodedString); 
	}

}
