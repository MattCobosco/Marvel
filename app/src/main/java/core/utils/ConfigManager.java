package core.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final String TAG = "ConfigManager";
    private static final String CONFIG_FILENAME = "config.properties";
    private static final String HISTORY_ENABLED_PROP = "HISTORY_ENABLED";
    private static final String DEFAULT_HISTORY_ENABLED = "true";

    private final Context context;
    private Properties configProperties;

    public ConfigManager(Context context) {
        this.context = context.getApplicationContext();
        checkFile();
        loadConfig();
    }

    private void checkFile() {
        File file = new File(context.getFilesDir(), CONFIG_FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                configProperties.setProperty(HISTORY_ENABLED_PROP, DEFAULT_HISTORY_ENABLED);
            } catch (IOException e) {
                Log.e(TAG, "Error creating config file", e);
            }
        }
    }

    public boolean isHistoryEnabled() {
        return Boolean.parseBoolean(configProperties.getProperty(HISTORY_ENABLED_PROP, DEFAULT_HISTORY_ENABLED));
    }

    public void setHistoryEnabled(boolean historyEnabled) {
        configProperties.setProperty(HISTORY_ENABLED_PROP, Boolean.toString(historyEnabled));
        saveConfig();
    }

    private void loadConfig() {
        configProperties = new Properties();
        try (FileInputStream in = context.openFileInput(CONFIG_FILENAME)) {
            configProperties.load(in);
        } catch (IOException e) {
            Log.e(TAG, "Error loading config", e);
        }
    }

    private void saveConfig() {
        try (FileOutputStream out = context.openFileOutput(CONFIG_FILENAME, Context.MODE_PRIVATE)) {
            configProperties.store(out, null);
        } catch (IOException e) {
            Log.e(TAG, "Error saving config", e);
        }
    }
}
