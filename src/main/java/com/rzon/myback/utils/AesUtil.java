package com.rzon.myback.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesUtil {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 偏移量，只有CBC模式才需要
     */
    private final static String ivParameter = "0000000000000000";

    /**
     * AES要求密钥长度为128位或192位或256位，java默认限制AES密钥长度最多128位
     */
    public static String sKey="0ki422ejx6f3s97h1u74s8t936t488bs";
    public static String mineKey="936t488bsh1u74s8t0ki422ejx6f3s97";

    /**
     * 编码格式
     */
    public static final String ENCODING = "utf-8";


    static {
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES加密
     * @param source    源字符串
     * @return  加密后的密文
     */
    public static String encrypt(String source) throws Exception {
        return getEncryped(source, sKey);
    }

    /**
     * AES解密
     * @param encryptStr    加密后的密文
     * @return  源字符串
     * @throws Exception
     */
    public static String decrypt(String encryptStr) throws Exception {
        byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        byte[] keyBytes = sKey.getBytes(ENCODING);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(ENCODING));
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, ENCODING);
    }

    /**
     * AES加密 二次加盐入库
     * @param source    源字符串
     * @return  加密后的密文
     * @throws Exception
     */
    public static String encryptDO(String source) throws Exception {
        return getEncryped(source, mineKey);
    }

    private static String getEncryped(String source, String mineKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] sourceBytes = source.getBytes(ENCODING);
        byte[] keyBytes = mineKey.getBytes(ENCODING);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(ENCODING));
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);
        byte[] decrypted = cipher.doFinal(sourceBytes);
        return Base64.encodeBase64String(decrypted);
    }
}