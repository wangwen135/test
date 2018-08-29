package com.wwh.test.password;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.wwh.test.codec.CodecUtils;

/**
 * <pre>
 * AES 编码器
 * 
 * 密钥长度：128位；加密方式：AES/ECB/PKCS5Padding
 * 
 * 可选：
 * 工作模式：ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128
 * 填充方式：NoPadding/PKCS5Padding/ISO10126Padding/
 * </pre>
 *
 * @author wwh
 * @date 2015年8月6日 上午11:16:33
 *
 */
public class AESCoder {

    /**
     * <pre>
     * 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
     * 
     * AES/CBC/NoPadding             16                                  不支持
     * 
     * AES/CBC/PKCS5Padding          32                                  16
     * 
     * AES/CBC/ISO10126Padding       32                              16
     * 
     * AES/CFB/NoPadding             16                              原始数据长度
     * 
     * AES/CFB/PKCS5Padding          32                                  16
     * 
     * AES/CFB/ISO10126Padding       32                              16
     * 
     * AES/ECB/NoPadding             16                                  不支持
     * 
     * AES/ECB/PKCS5Padding          32                              16
     * 
     * AES/ECB/ISO10126Padding       32                              16
     * 
     * AES/OFB/NoPadding             16                              原始数据长度
     * 
     * AES/OFB/PKCS5Padding          32                              16
     * 
     * AES/OFB/ISO10126Padding       32                              16
     * 
     * AES/PCBC/NoPadding            16                              不支持
     * 
     * AES/PCBC/PKCS5Padding         32                              16
     * 
     * AES/PCBC/ISO10126Padding      32                          16
     * </pre>
     */

    /**
     * 字符集
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE_128 = 128;

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 算法/模式/填充
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * <pre>
     * 根据密码获取密钥
     * 通过对密码进行MD5 得到16个byte
     * 使用UTF-8 编码将字符串转成byte[]
     * </pre>
     * 
     * @param password
     *            密码
     * @return 16个byte 128位的密钥
     * @throws Exception
     */
    public static byte[] getKeyByPassword(String password) throws Exception {
        return CodecUtils.md5(password.getBytes(DEFAULT_CHARSET));
    }

    /**
     * 根据种子获取随机密钥
     * 
     * @param seed
     *            种子
     * @return 128位的密钥
     * @throws Exception
     */
    public static byte[] getRandomKeyBySeed(byte[] seed) throws Exception {
        KeyGenerator kg = getRandomKeyGenerator(seed);

        // 生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 返回此密钥的密钥内容
        return secretKey.getEncoded();
    }

    /**
     * 根据种子获得一个随机密钥生成器
     * 
     * @param seed
     *            种子
     * @return 随机密钥生成器
     * @throws NoSuchAlgorithmException
     */
    public static KeyGenerator getRandomKeyGenerator(byte[] seed) throws NoSuchAlgorithmException {
        // 返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // AES 要求密钥长度为 128
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed);
        // /new SecureRandom(seed)
        kg.init(KEY_SIZE_128, new SecureRandom(seed));
        return kg;
    }

    /**
     * 获取随机的密钥
     * 
     * @return 128位的密钥
     * @throws Exception
     */
    public static byte[] getRandomKey() throws Exception {
        // 返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // AES 要求密钥长度为 128
        kg.init(KEY_SIZE_128);
        // 生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 返回此密钥的密钥内容
        return secretKey.getEncoded();

    }

    /**
     * 根据给定的字节数组构造一个AES密钥
     *
     * @param key
     *            密钥的密钥内容，复制该数组的内容来防止后续修改
     * @return 密钥
     */
    public static Key getSecretKeySpec(byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥对象
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            二进制密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            二进制密钥
     * @param cipherAlgorithm
     *            加密算法/工作模式/填充方式
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        // 还原密钥
        Key k = getSecretKeySpec(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    /**
     * 加密
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥对象
     * @param cipherAlgorithm
     *            加密算法/工作模式/填充方式
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        // 实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        // 使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            二进制密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥对象
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            二进制密钥
     * @param cipherAlgorithm
     *            加密算法/工作模式/填充方式
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        // 还原密钥
        Key k = getSecretKeySpec(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    /**
     * 解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @param cipherAlgorithm
     *            加密算法/工作模式/填充方式
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        // 实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        // 使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 将byte[]转成字符串
     * 
     * @param data
     * @return
     */
    public static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String pwd = "123456789";
        byte[] key = getKeyByPassword(pwd);
        System.out.println("密钥 base64：" + CodecUtils.base64Encode(key));
        System.out.println("密钥 hex：" + CodecUtils.hexEncode(key));
        System.out.println();
        
        String data = "测试测试 啊啊啊啊啊啊啊asdfadf";
        byte[] ret = encrypt(data.getBytes(), key);

        System.out.println("结果 base64：" + CodecUtils.base64Encode(ret));
        System.out.println("结果 hex：" + CodecUtils.hexEncode(ret));

    }

    public static void main1(String[] args) throws Exception {
        String keyStr = "1234567890";
        System.out.println("密码：" + keyStr);
        byte[] key = null;

        // 获取密钥生成器
        KeyGenerator kg = getRandomKeyGenerator(keyStr.getBytes(DEFAULT_CHARSET));

        // for (int i = 1; i < 11; i++) {
        // System.out.println("生成密钥 "+i);
        // 生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 返回此密钥的密钥内容
        key = secretKey.getEncoded();

        System.out.println("密钥 base64 ：" + CodecUtils.base64Encode(key));
        System.out.println("密钥 hex ：" + CodecUtils.hexEncode(key));
        // }

        System.out.println();
        System.out.println("使用密钥：" + CodecUtils.base64Encode(key));
        Key k = getSecretKeySpec(key);
        System.out.println("新生成的密钥内容是：" + CodecUtils.base64Encode(k.getEncoded()));
        System.out.println();
        String data = "AES encrypt test 123456789";

        System.out.println("加密前数据: string : " + data);
        final byte[] bytes = data.getBytes();
        System.out.println("加密前数据: base64 : " + CodecUtils.base64Encode(bytes));
        System.out.println("加密前数据: hex : " + CodecUtils.hexEncode(bytes));
        System.out.println();
        byte[] encryptData = encrypt(bytes, k);
        System.out.println("加密后数据: base64 : " + CodecUtils.base64Encode(encryptData));
        System.out.println("加密后数据: hex : " + CodecUtils.hexEncode(encryptData));

        System.out.println();
        // System.out.println("重新生成密钥");
        // // 生成一个密钥
        // SecretKey secretKey = kg.generateKey();
        // // 返回此密钥的密钥内容
        // key = secretKey.getEncoded();
        //
        // System.out.println("密钥 byte[ ：" + showByteArray(key));
        // System.out.println("密钥 base64 ：" + CodecUtils.base64Encode(key));
        // k = getSecretKeySpec(key);
        byte[] decryptData = decrypt(encryptData, k);
        System.out.println("解密后数据: base64 : " + CodecUtils.base64Encode(decryptData));
        System.out.println("解密后数据: hex : " + CodecUtils.hexEncode(decryptData));

        System.out.println("解密后数据: string : " + new String(decryptData));

    }
}