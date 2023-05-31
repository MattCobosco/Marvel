package pl.wsei.marvel;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import core.utils.ConfigManager;

@RunWith(AndroidJUnit4.class)
public class ConfigManagerTest {
    private static final String CONFIG_FILENAME = "config.properties";

    private ConfigManager configManager;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        configManager = new ConfigManager(context);
    }

    @Test
    public void testSetHistoryEnabled() {
        configManager.setHistoryEnabled(true);
        boolean historyEnabled = configManager.isHistoryEnabled();
        assertTrue(historyEnabled);
    }

    @Test
    public void testSetHistoryEnabledFalse() {
        configManager.setHistoryEnabled(false);
        boolean historyEnabled = configManager.isHistoryEnabled();
        assertFalse(historyEnabled);
    }

    @Test
    public void testFileCreation() {
        Context context = ApplicationProvider.getApplicationContext();
        File file = new File(context.getFilesDir(), CONFIG_FILENAME);
        assertTrue(file.exists());
    }

    @After
    public void cleanup() {
        configManager.setHistoryEnabled(true);
    }
}