package ru.clevertec.checkrunner.util.factory;

import ru.clevertec.checkrunner.util.cache.Cache;

public interface Factory<K, V> {

    Cache<K, V> getCache(String cacheType, int capacity);
}
