package pl.wsei.marvel;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;

import core.api.utils.ApiKeysManager;

@RunWith(AndroidJUnit4.class)
public class ApiKeysManagerTest {
    private ApiKeysManager apiKeysManager;
    private String publicKey;
    private String privateKey;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        apiKeysManager = new ApiKeysManager(context);
        publicKey = apiKeysManager.getPublicKey();
        privateKey = apiKeysManager.getPrivateKey();
    }


    @Test
    public void testSetKeys() {
        apiKeysManager.setPublicKey("new_public_key");
        apiKeysManager.setPrivateKey("new_private_key");

        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        assertEquals("new_public_key", publicKey);
        assertEquals("new_private_key", privateKey);
    }

    @Test
    public void testFileCreation() {
        Context context = ApplicationProvider.getApplicationContext();
        File file = new File(context.getFilesDir(), "apikey.properties");
        assertTrue(file.exists());
    }

    @After
    public void cleanup() {
        apiKeysManager.setPublicKey(this.publicKey);
        apiKeysManager.setPrivateKey(this.privateKey);
    }
}