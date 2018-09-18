package com.shenhesoft.examsapp.util;

import android.text.TextUtils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import cn.droidlover.xdroidmvp.log.XLog;

/**
 * .
 * <p>
 * <a href="RSAClientHelper.java"><i>View Source</i></a>
 * </p>
 *
 * @author LiuJiefeng
 * @version 1.0.0
 * @date 2018-04-13
 * @since 1.0.0
 */
public class RSAClientHelper {

    private static final String ALGORITHOM = "RSA";
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    private static KeyFactory keyFactory = null;

    static {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            XLog.e(ex.getMessage());
        }
    }

    private RSAClientHelper() {
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus        系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    private static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec =
                new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            XLog.e("RSAPublicKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            XLog.e("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }


    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     *
     * @param hexModulus        系数。
     * @param hexPublicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    private static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if (TextUtils.isEmpty(hexModulus) || TextUtils.isEmpty(hexPublicExponent)) {
            XLog.e("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
        } catch (DecoderException ex) {
            XLog.e("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
        }
        if (modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data      要加密的数据。
     * @return 加密后的数据。
     */
    private static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    /**
     * 使用给定的公钥加密给定的字符串。
     * <p/>
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回 {@code
     * null}。
     *
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    private static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            XLog.e(ex.getCause().getMessage());
        }
        return null;
    }


    public static String getSessionId(String username, String passwd) {
        String sessionId = md5(username + passwd);
        return sessionId;
    }

    private static String md5(String text) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(text.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return sb.append(new String()).toString();
    }

    public static String getPassword(String m, String e, String pwd) {
        RSAPublicKey rsa = getRSAPublidKey(m, e);
        return encryptString(rsa, pwd);
    }
}
