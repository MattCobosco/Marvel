package core.api.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKeysManager {
    private static final String TAG = "ApiKeysManager";
    private static final String APIKEY_FILENAME = "apikey.properties";
    private static final String PUBLIC_KEY_PROP = "PUBLIC_KEY";
    private static final String PRIVATE_KEY_PROP = "PRIVATE_KEY";
    private static final String DEFAULT_PUBLIC_KEY = "default_public_key";
    private static final String DEFAULT_PRIVATE_KEY = "default_private_key";

    private final Context context;
    private Properties apikeyProperties;

    public ApiKeysManager(Context context) {
        this.context = context.getApplicationContext();
        checkFile();
        loadApiKeys();
    }

    private void checkFile() {
        File file = new File(context.getFilesDir(), APIKEY_FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                apikeyProperties.setProperty(PUBLIC_KEY_PROP, DEFAULT_PUBLIC_KEY);
                apikeyProperties.setProperty(PRIVATE_KEY_PROP, DEFAULT_PRIVATE_KEY);
            } catch (IOException e) {
                Log.e(TAG, "Error creating API keys file", e);
            }
        }
    }


    public String getPublicKey() {
        return apikeyProperties.getProperty(PUBLIC_KEY_PROP, DEFAULT_PUBLIC_KEY);
    }

    public String getPrivateKey() {
        return apikeyProperties.getProperty(PRIVATE_KEY_PROP, DEFAULT_PRIVATE_KEY);
    }

    public void setPublicKey(String publicKey) {
        apikeyProperties.setProperty(PUBLIC_KEY_PROP, publicKey);
        saveApiKeys();
    }

    public void setPrivateKey(String privateKey) {
        apikeyProperties.setProperty(PRIVATE_KEY_PROP, privateKey);
        saveApiKeys();
    }

    private void loadApiKeys() {
        apikeyProperties = new Properties();
        try (FileInputStream in = context.openFileInput(APIKEY_FILENAME)) {
            apikeyProperties.load(in);
        } catch (IOException e) {
            Log.e(TAG, "Error loading API keys", e);
        }
    }

    private void saveApiKeys() {
        try (FileOutputStream out = context.openFileOutput(APIKEY_FILENAME, Context.MODE_PRIVATE)) {
            apikeyProperties.store(out, null);
        } catch (IOException e) {
            Log.e(TAG, "Error saving API keys", e);
        }
    }

    public String getTAG() {
        return TAG;
    }

    public String getAPIKEY_FILENAME() {
        return APIKEY_FILENAME;
    }

    public String getPUBLIC_KEY_PROP() {
        return PUBLIC_KEY_PROP;
    }

    public String getPRIVATE_KEY_PROP() {
        return PRIVATE_KEY_PROP;
    }

    public String getDEFAULT_PUBLIC_KEY() {
        return DEFAULT_PUBLIC_KEY;
    }

    public String getDEFAULT_PRIVATE_KEY() {
        return DEFAULT_PRIVATE_KEY;
    }

    public Context getContext() {
        return context;
    }
}