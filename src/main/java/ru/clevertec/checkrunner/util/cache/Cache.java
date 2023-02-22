package ru.clevertec.checkrunner.util.cache;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    int size();

    boolean isEmpty();

    void clear();
}
