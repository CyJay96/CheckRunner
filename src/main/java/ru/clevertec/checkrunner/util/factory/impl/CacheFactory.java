package ru.clevertec.checkrunner.util.factory.impl;

import ru.clevertec.checkrunner.util.cache.Cache;
import ru.clevertec.checkrunner.util.cache.impl.LfuCache;
import ru.clevertec.checkrunner.util.cache.impl.LruCache;
import ru.clevertec.checkrunner.util.factory.Factory;

import static ru.clevertec.checkrunner.util.Constants.LFU_CACHE;
import static ru.clevertec.checkrunner.util.Constants.LRU_CACHE;

public class CacheFactory<K, V> implements Factory<K, V> {

    @Override
    public Cache<K, V> getCache(String cacheType, int capacity) {
        return switch (cacheType) {
            case LRU_CACHE -> new LruCache<>(capacity);
            case LFU_CACHE -> new LfuCache<>(capacity);
            default -> throw new IllegalArgumentException("Unknown cache type");
        };
    }
}
