package ru.otus.professional.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private static final int CURRENT_METHOD_POSITION = 4;

    private final Map<String, V> container;
    private final List<HwListener<K, V>> listeners;

    public MyCache(Map<String, V> container) {
        this.container = container;
        listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        container.put(key.toString(), value);
        listeners.forEach(l -> l.notify(key, value, getCurrentMethodName()));
    }

    @Override
    public void remove(K key) {
        var val = container.remove(createCacheKey(key));
        listeners.forEach(l -> l.notify(key, val, getCurrentMethodName()));
    }

    @Override
    public V get(K key) {
        var val = container.get(createCacheKey(key));
        listeners.forEach(l -> l.notify(key, val, getCurrentMethodName()));
        return val;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private String getCurrentMethodName() {
        return Thread.currentThread()
                .getStackTrace()[CURRENT_METHOD_POSITION].getMethodName();
    }

    private String createCacheKey(K key) {
        return key.toString();
    }

}