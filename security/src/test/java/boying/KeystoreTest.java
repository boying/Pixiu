package boying;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by boying on 2017/10/31.
 */
public class KeystoreTest {
    @Test
    public void test() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        InputStream in = new ClassPathResource("keystore.jks").getInputStream();
        keyStore.load(in, "boying".toCharArray());
        PublicKey publicKey = keyStore.getCertificate("login").getPublicKey();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("login", "boying".toCharArray());

        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        String publicKeyString  = RSACoder.encryptBASE64(publicKey.getEncoded());
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKeyString);
        String privateKeyString = RSACoder.encryptBASE64(privateKey.getEncoded());
        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKeyString);

        System.err.println("公钥：" + publicKeyString);
        System.err.println("私钥：" + privateKeyString);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "加密后：" + new String(encodedData) + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

    }
}
