package com.sandboxol.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * @author : dongjunpeng
 * @date : 2019/6/13 17:41
 */
public class SymmetricEncryptUtil {

    /**
     * 加密
     * @param content 需加密的内容
     * @param cipherText 密文
     * @return
     */
    public static String encryption(String content, String cipherText) {
        return parseByte2HexStr(getEncryptByteArr(content, cipherText));
    }

//    public static String decryption(String content, String cipherText) {
//        return new String(Objects.requireNonNull(getDecryptByteArr(parseHexStr2Byte(content), cipherText)));
//    }

    /**
     * 获取加密后byte数组
     * @param content
     * @param password
     * @return
     */
    private static byte[] getEncryptByteArr(String content, String password) {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kGen.init(128, secureRandom);
//            kGen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            return cipher.doFinal(byteContent); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    /**
     * 获取解密后byte数组
     * @param content
     * @param password
     * @return
     */
//    private static byte[] getDecryptByteArr(byte[] content, String password) {
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            secureRandom.setSeed(password.getBytes());
//            kgen.init(128, secureRandom);
////            kgen.init(128, new SecureRandom(password.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(content);
//            return result; // 加密
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
//    private static byte[] parseHexStr2Byte(String hexStr) {
//        if (hexStr.length() < 1)
//            return null;
//        byte[] result = new byte[hexStr.length() / 2];
//        for (int i = 0; i < hexStr.length() / 2; i++) {
//            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
//            result[i] = (byte) (high * 16 + low);
//        }
//        return result;
//    }
}
