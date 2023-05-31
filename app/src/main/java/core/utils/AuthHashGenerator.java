package core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthHashGenerator {
    public String generateHash(final String timestamp, final String publicKey, final String privateKey)
    throws RuntimeException {
        try {
            final String value = timestamp + privateKey + publicKey;
            final MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            final byte[] md5Bytes = md5Encoder.digest(value.getBytes(StandardCharsets.UTF_8));

            final StringBuilder md5 = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                md5.append(Integer.toHexString((md5Byte & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("authHashGenerator failed:", e);
        }
    }
}
