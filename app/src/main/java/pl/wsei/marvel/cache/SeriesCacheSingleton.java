package pl.wsei.marvel.cache;

import android.util.LruCache;

import java.util.List;

import core.api.models.SerieRow;

public class SeriesCacheSingleton {
    private static SeriesCacheSingleton instance;
    private final LruCache<String, List<SerieRow>> cache;

    private SeriesCacheSingleton() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        this.cache = new LruCache<>(cacheSize);
    }

    public static synchronized SeriesCacheSingleton getInstance() {
        if (null == instance) {
            instance = new SeriesCacheSingleton();
        }
        return instance;
    }

    public LruCache<String, List<SerieRow>> getCache() {
        return this.cache;
    }
}
