/**
 * Copyright(C) 2015-2025 杏仁科技
 * All rights reserved
 * 2015年8月6日 Created
 */
package com.wwh.password.encrypt;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年8月6日 上午11:28:08
 *
 */
public interface EncryptUtilApi {
    // ------MD5-------//
    String MD5(String res);

    String MD5(String res, String key);

    // ------SHA1-------//
    String SHA1(String res);

    String SHA1(String res, String key);

    // ------DES-------//
    String DESencode(String res, String key);

    String DESdecode(String res, String key);

    // ------AES-------//
    String AESencode(String res, String key);

    String AESdecode(String res, String key);

    // ------异或加密-----//
    String XORencode(String res, String key);

    String XORdecode(String res, String key);

    int XOR(int res, String key);
}
