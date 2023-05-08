package pl.wsei.marvel.cache;

import android.util.LruCache;

import java.util.List;

import core.api.models.CharacterRow;

public class CharacterCacheSingleton {
    private static CharacterCacheSingleton instance;
    private final LruCache<String, List<CharacterRow>> cache;

    private CharacterCacheSingleton() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        this.cache = new LruCache<>(cacheSize);
    }

    public static synchronized CharacterCacheSingleton getInstance() {
        if (null == instance) {
            instance = new CharacterCacheSingleton();
        }
        return instance;
    }

    public LruCache<String, List<CharacterRow>> getCache() {
        return this.cache;
    }
}