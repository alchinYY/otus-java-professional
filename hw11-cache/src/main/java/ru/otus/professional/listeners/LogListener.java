package ru.otus.professional.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.professional.cache.HwListener;

public class LogListener<K, V> implements HwListener<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(LogListener.class);

    @Override
    public void notify(K key, V value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
    }
}
