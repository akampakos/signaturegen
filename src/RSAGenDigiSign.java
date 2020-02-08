import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;


public class RSAGenDigiSign {

    private static final String ALGORITHM = "RSA";
    private static String signature = "";

    public static String GenerateSignature(String inputFile, String privateKey) {
        try {

            //Change private key to bytes
            byte[] privateKeyByteArr = Base64.decode(privateKey);
            PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKeyByteArr));

            Signature rsaSign = Signature.getInstance("SHA1withRSA");

            rsaSign.initSign(key);

            BufferedInputStream bufin = new BufferedInputStream(new FileInputStream(inputFile));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                rsaSign.update(buffer, 0, len);
            }
            bufin.close();

            byte[] realSign = rsaSign.sign();


            //******************************************************************************************
            BASE64Encoder encoder = new BASE64Encoder();
            //encoder.encodeBuffer(realSign, out);
            signature = encoder.encodeBuffer(realSign);
            //******************************************************************************************

        } catch (Exception e) {
            signature = "ERROR: " + e.toString();
            return signature;
        }

        return signature;

    }
}
