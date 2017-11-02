package boying;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.security.Security;
import java.util.Map;

/**
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class RSACoderTest {
    private String publicKey;
    private String privateKey;

    private String pemPublicKey;
    private String pkcs1PrivateKey;
    private String pkcs8PrivateKey;


    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        // System.err.println("公钥: \n\r" + publicKey);
        // System.err.println("私钥： \n\r" + privateKey);

        pemPublicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpt3OHkcYLlJQAb73GNKDz/jOE\n" +
                "fCoW7eu0IhKWqjV03ykAPXWlxl9I8rjtTjons3edvXq+/reercjShX02IzQNss1H\n" +
                "Mls2r8VSeUzJuxM0er1rs7gwXAE39nG+mwqaGaYuGRzwQ3M1JqCNrbFqljPTQ/d+\n" +
                "aNRPuQLwCt8ofcd2iQIDAQAB\n" +
                "-----END PUBLIC KEY-----";

        pkcs1PrivateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIICXQIBAAKBgQCpt3OHkcYLlJQAb73GNKDz/jOEfCoW7eu0IhKWqjV03ykAPXWl\n" +
                "xl9I8rjtTjons3edvXq+/reercjShX02IzQNss1HMls2r8VSeUzJuxM0er1rs7gw\n" +
                "XAE39nG+mwqaGaYuGRzwQ3M1JqCNrbFqljPTQ/d+aNRPuQLwCt8ofcd2iQIDAQAB\n" +
                "AoGARj8ObJCjvmtTDxMxI3bpH9SZkj6qVS9JHhf0RbRRx/wORnEp3uHK0rDUZjS4\n" +
                "0XHxErM4uRFJAz6/HpPuDZholI4MMaCiIG6shwy5/2WCOT14XCaCOqlqDgiJnCGj\n" +
                "rjX8oz6R3cGnYcB7K+m9N3/RoJQDt0Fnpcu2nGuqI4HTQVkCQQDdjAjOr2NR2bm9\n" +
                "B/z/v/ocMqFsfkr6Gt9T/MDodS8FYo4GkaOkysWsWfcAh3Z7Ckhyo7hQVzYXq/7V\n" +
                "v3JzLZsLAkEAxBwBsuQx4n0HugztziDJicT3UwnKsltM2eCDz4odF17ZoSIk7i+a\n" +
                "pVIUZLYgJnoAiy93Jnw9Snk8koLxtIsROwJBANnKkdXtYOvT593GfpZEeXFC7KGl\n" +
                "PbvEswrzjAh5OnJGwmv+vCqCE/Uss77XbEfkgfgArm58bXaz5F7oR3CmwqUCQGjs\n" +
                "cxw7a1U6f//TGwjGEg5cC9epzYFPx/ZqYRuRUa9HWDkWA5xMa/k3ySF5MApmDBRr\n" +
                "+NvmbQSDiRz6YkeugDECQQDZCykumdM57EvP9nqaK1o44bKVFe9JgSwR6QVH4b/r\n" +
                "o0aWAR7JG9oFx5PhZl5nsjPqFZV2IdVw5nUvaI2fHZp1\n" +
                "-----END RSA PRIVATE KEY-----";

        pkcs8PrivateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKm3c4eRxguUlABv\n" +
                "vcY0oPP+M4R8Khbt67QiEpaqNXTfKQA9daXGX0jyuO1OOiezd529er7+t56tyNKF\n" +
                "fTYjNA2yzUcyWzavxVJ5TMm7EzR6vWuzuDBcATf2cb6bCpoZpi4ZHPBDczUmoI2t\n" +
                "sWqWM9ND935o1E+5AvAK3yh9x3aJAgMBAAECgYBGPw5skKO+a1MPEzEjdukf1JmS\n" +
                "PqpVL0keF/RFtFHH/A5GcSne4crSsNRmNLjRcfESszi5EUkDPr8ek+4NmGiUjgwx\n" +
                "oKIgbqyHDLn/ZYI5PXhcJoI6qWoOCImcIaOuNfyjPpHdwadhwHsr6b03f9GglAO3\n" +
                "QWely7aca6ojgdNBWQJBAN2MCM6vY1HZub0H/P+/+hwyoWx+Svoa31P8wOh1LwVi\n" +
                "jgaRo6TKxaxZ9wCHdnsKSHKjuFBXNher/tW/cnMtmwsCQQDEHAGy5DHifQe6DO3O\n" +
                "IMmJxPdTCcqyW0zZ4IPPih0XXtmhIiTuL5qlUhRktiAmegCLL3cmfD1KeTySgvG0\n" +
                "ixE7AkEA2cqR1e1g69Pn3cZ+lkR5cULsoaU9u8SzCvOMCHk6ckbCa/68KoIT9Syz\n" +
                "vtdsR+SB+ACubnxtdrPkXuhHcKbCpQJAaOxzHDtrVTp//9MbCMYSDlwL16nNgU/H\n" +
                "9mphG5FRr0dYORYDnExr+TfJIXkwCmYMFGv42+ZtBIOJHPpiR66AMQJBANkLKS6Z\n" +
                "0znsS8/2eporWjjhspUV70mBLBHpBUfhv+ujRpYBHskb2gXHk+FmXmeyM+oVlXYh\n" +
                "1XDmdS9ojZ8dmnU=\n" +
                "-----END PRIVATE KEY-----\n";
    }

    @Test
    public void decryptByPKCS1PrivateKeyTest() throws Exception {
        String data = "hello";
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] bytes = RSACoder.encryptByPublicKey(data.getBytes(), pemPublicKey);

        byte[] bytes1 = RSACoder.decryptByPKCS1PrivateKey(bytes, pkcs1PrivateKey);
        assertEquals(data, new String(bytes1));
    }

    @Test
    public void decryptByPKCS8PrivateKeyTest() throws Exception {
        String data = "hello";
        byte[] bytes = RSACoder.encryptByPublicKey(data.getBytes(), pemPublicKey);
        byte[] bytes1 = RSACoder.decryptByPKCS8PrivateKey(bytes, pkcs8PrivateKey);
        assertEquals(data, new String(bytes1));
    }

    @Test
    public void decryptByPrivateKeyTest() throws Exception{
        String data = "hello";
        byte[] bytes = RSACoder.encryptByPublicKey(data.getBytes(), pemPublicKey);
        byte[] bytes1 = RSACoder.decryptByPrivateKey(bytes, pkcs1PrivateKey);
        assertEquals(data, new String(bytes1));

        byte[] bytes2 = RSACoder.decryptByPrivateKey(bytes, pkcs8PrivateKey);
        assertEquals(data, new String(bytes2));
    }

    @Test
    public void test2() throws Exception{
        String decrypted = "g6Ejq6w2IiFeNn+1gDfrAsUwcyzdI3BBInnqhYDSU11ZDLVsqgEs5GOSnA3YI/mbkCBuJicUZWXz4FFVtrLEQVy/8C38+G7cqMHKgX9MS/8tAief8iGB9QtxppFIm4mkJQq+suM+TGMY8sBtJZVXs1t3htBYrjykHs1r0oTxHR8=";
        byte[] bytes = RSACoder.decryptByPKCS8PrivateKey(RSACoder.decryptBASE64(decrypted), pkcs8PrivateKey);
        System.out.println(new String(bytes));
    }

    @Test
    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
                privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "加密后：" + new String(encodedData) + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

    }

    @Test
    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

        byte[] decodedData = RSACoder
                .decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);

        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);
        assertTrue(status);

    }

}
