package pl.wsei.marvel.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.utils.AuthHashGenerator;

public class AuthHashGeneratorTest {
    @Test
    public void testGenerateHash() {
        AuthHashGenerator authHashGenerator = new AuthHashGenerator();
        String timestamp = "2023-05-31T12:00:00Z";
        String publicKey = "publicKey";
        String privateKey = "privateKey";
        String expectedHash = "d1db6d95b435aaa0d7d6e76675440d68";

        String actualHash = authHashGenerator.generateHash(timestamp, publicKey, privateKey);

        assertEquals(expectedHash, actualHash);
    }
}