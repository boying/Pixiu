package boying;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoder extends Coder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final String PKCS1_HEADER = "BEGIN RSA PRIVATE KEY";
    private static final String PKCS1_FOOTER = "END RSA PRIVATE KEY";
    private static final String PKCS8_HEADER = "BEGIN PRIVATE KEY";
    private static final String PKCS8_FOOTER = "END PRIVATE KEY";
    private static final String PUBLIC_KEY_HEADER = "BEGIN PUBLIC KEY";
    private static final String PUBLIC_KEY_FOOTER = "END PUBLIC KEY";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        if (isPKCS8Key(key)) {
            return decryptByPKCS8PrivateKey(data, key);
        } else if (isPKCS1Key(key)) {
            return decryptByPKCS1PrivateKey(data, key);
        }
        return decryptByPKCS8PrivateKey(data, key);
    }

    /**
     * 用PKCS1的秘钥解密
     * reffer：
     * <p>
     * https://stackoverflow.com/questions/7216969/getting-rsa-private-key-from-pem-base64-encoded-private-key-file
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPKCS1PrivateKey(byte[] data, String key) throws Exception {
        String removeHeaderFooterKey = removeHeaderFooterLineBreak(key);

        // 对密钥解密
        byte[] keyBytes = decryptBASE64(removeHeaderFooterKey);

        ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence
                .fromByteArray(keyBytes);
        Enumeration<?> e = primitive.getObjects();
        BigInteger v = ((ASN1Integer) e.nextElement()).getValue();

        int version = v.intValue();
        if (version != 0 && version != 1) {
            throw new IllegalArgumentException("wrong version for RSA private key");
        }

        BigInteger modulus = ((ASN1Integer) e.nextElement()).getValue();
        e.nextElement();
        BigInteger privateExponent = ((ASN1Integer) e.nextElement()).getValue();

        RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA"); // RSA/ECB/PKCS1Padding ok too
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }


    public static byte[] decryptByPKCS8PrivateKey(byte[] data, String key) throws Exception {
        String removeHeaderFooterKey = removeHeaderFooterLineBreak(key);
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(removeHeaderFooterKey);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密  
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        String s = removeHeaderFooterLineBreak(key);
        // 对公钥解密  
        byte[] keyBytes = decryptBASE64(s);

        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密

        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static boolean isPKCS1Key(String privateKey) {
        return privateKey.contains(PKCS1_HEADER);
    }

    public static boolean isPKCS8Key(String privateKey) {
        return privateKey.contains(PKCS8_HEADER);
    }

    private static String removeHeaderFooterLineBreak(String key) {
        String[] lines = key.split("\n");
        StringBuffer sb = new StringBuffer();
        for (String line : lines) {
            line = line.replace("\r", "");
            line = line.trim();
            if ("".equals(line) || line.contains(PKCS1_HEADER) || line.contains(PKCS1_FOOTER)
                    || line.contains(PKCS8_HEADER) || line.contains(PKCS8_FOOTER)
                    || line.contains(PUBLIC_KEY_HEADER) || line.contains(PUBLIC_KEY_FOOTER)) {
                continue;
            }
            sb.append(line);
        }
        return sb.toString();
    }
}