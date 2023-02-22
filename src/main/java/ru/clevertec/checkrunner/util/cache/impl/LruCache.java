package ru.clevertec.checkrunner.util.cache.impl;

import ru.clevertec.checkrunner.exception.DataNotFoundException;
import ru.clevertec.checkrunner.util.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * An LRU (Least Recently Used) cache implementation that uses a ConcurrentHashMap
 * to store key-value pairs and a ConcurrentLinkedDeque to store keys.
 * Concurrent library provides thread-safety.
 *
 * @param <K> the type of keys stored in the cache
 * @param <V> the type of values stored in the cache
 *
 * @author Konstantin Voytko
 */
public class LruCache<K, V> implements Cache<K, V> {

    // Maximum capacity of the cache
    private final int capacity;

    // Map to store the key-value pairs
    private final Map<K, V> cache;

    // ConcurrentLinkedDeque to store the keys
    private final ConcurrentLinkedDeque<K> keys;

    /**
     * Constructs a new LRU cache with the specified capacity.
     *
     * @param capacity the maximum number of key-value pairs that can be stored in the cache
     * @throws IllegalArgumentException if the capacity is negative
     */
    public LruCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity in LRU cache is negative");
        }
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>(capacity);
        keys = new ConcurrentLinkedDeque<>();
    }

    /**
     * Associates the specified value with the specified key in this cache.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @throws NullPointerException if the key or value is null
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("Key or Value in LRU cache is null");
        }
        if (cache.containsValue(value)) {
            keys.remove(key);
        }
        while (cache.size() >= capacity) {
            K evictedKey = keys.removeLast();
            cache.remove(evictedKey);
        }
        cache.put(key, value);
        keys.addFirst(key);
    }

    /**
     * Returns the value to which the specified key is mapped.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     * @throws NullPointerException if the key is null
     * @throws DataNotFoundException if the value is null
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        V value = cache.get(key);
        if (value == null) {
            throw new DataNotFoundException("Key");
        }
        keys.remove(key);
        keys.addFirst(key);
        return value;
    }

    /**
     * Returns the current size of cache.
     *
     * @return the current size of cache
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * Checks if the current cache is empty.
     *
     * @return a Boolean value of whether the current cache is empty
     */
    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    /**
     * Clears the current cache.
     */
    @Override
    public void clear() {
        cache.clear();
        keys.clear();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
