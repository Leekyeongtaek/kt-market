package com.market.ktmarket.common.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

    public static String encodeSHA256(String str) {
        return Hex.encodeHexString(DigestUtils.getDigest("SHA-256").digest(str.getBytes()));
    }
}
