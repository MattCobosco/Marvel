package pl.wsei.marvel.cache;

import android.util.LruCache;

import java.util.List;

import core.api.models.CreatorRow;

public class CreatorsCacheSingleton {
    private static CreatorsCacheSingleton instance;
    private final LruCache<String, List<CreatorRow>> cache;

    private CreatorsCacheSingleton() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        this.cache = new LruCache<>(cacheSize);
    }

    public static synchronized CreatorsCacheSingleton getInstance() {
        if (null == instance) {
            instance = new CreatorsCacheSingleton();
        }
        return instance;
    }

    public LruCache<String, List<CreatorRow>> getCache() {
        return this.cache;
    }
}
