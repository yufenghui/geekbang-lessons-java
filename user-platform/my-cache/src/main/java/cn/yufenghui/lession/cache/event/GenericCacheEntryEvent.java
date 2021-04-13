package cn.yufenghui.lession.cache.event;

import javax.cache.Cache;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.EventType;

import static java.util.Objects.requireNonNull;

/**
 * @author Yu Fenghui
 * @date 2021/4/13 17:42
 * @since
 */
public class GenericCacheEntryEvent<K, V> extends CacheEntryEvent<K, V> {

    private final K key;

    private final V oldValue;

    private final V value;

    /**
     * Constructs a cache entry event from a given cache as source
     *
     * @param source    the cache that originated the event
     * @param eventType the event type for this event
     * @param key       the key of {@link javax.cache.Cache.Entry}
     * @param oldValue  the old value of {@link javax.cache.Cache.Entry}
     * @param value     the current value of {@link javax.cache.Cache.Entry}
     */
    public GenericCacheEntryEvent(Cache source, EventType eventType, K key, V oldValue, V value) {
        super(source, eventType);
        requireNonNull(key, "The key must not be null!");
        requireNonNull(value, "The value must not be null!");
        this.key = key;
        this.oldValue = oldValue;
        this.value = value;
    }

    @Override
    public V getValue() {
        return null;
    }

    @Override
    public V getOldValue() {
        return null;
    }

    @Override
    public boolean isOldValueAvailable() {
        return false;
    }

    @Override
    public K getKey() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        return null;
    }

    public static <K, V> CacheEntryEvent<K, V> createdEvent(Cache source, K key, V value) {
        return of(source, EventType.CREATED, key, null, value);
    }

    public static <K, V> CacheEntryEvent<K, V> updatedEvent(Cache source, K key, V oldValue, V value) {
        return of(source, EventType.UPDATED, key, oldValue, value);
    }

    public static <K, V> CacheEntryEvent<K, V> expiredEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.EXPIRED, key, oldValue, oldValue);
    }

    public static <K, V> CacheEntryEvent<K, V> removedEvent(Cache source, K key, V oldValue) {
        return of(source, EventType.REMOVED, key, oldValue, oldValue);
    }

    public static <K, V> CacheEntryEvent<K, V> of(Cache source, EventType eventType, K key, V oldValue, V value) {
        return new GenericCacheEntryEvent<>(source, eventType, key, oldValue, value);
    }

}
