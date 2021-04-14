package cn.yufenghui.lession.cache.event;

import javax.cache.configuration.Factory;
import javax.cache.event.*;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 9:23
 * @since
 */
public class TestCacheEntryListener<K, V> implements CacheEntryCreatedListener<K, V>, CacheEntryUpdatedListener<K, V>,
        CacheEntryExpiredListener<K, V>, CacheEntryRemovedListener<K, V>,
        Factory<CacheEntryListener<K, V>> {

    @Override
    public CacheEntryListener<K, V> create() {
        return this;
    }

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends K, ? extends V>> cacheEntryEvents) throws CacheEntryListenerException {
        println("onCreated", cacheEntryEvents);
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> cacheEntryEvents) throws CacheEntryListenerException {
        println("onExpired", cacheEntryEvents);
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> cacheEntryEvents) throws CacheEntryListenerException {
        println("onRemoved", cacheEntryEvents);
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends K, ? extends V>> cacheEntryEvents) throws CacheEntryListenerException {
        println("onUpdated", cacheEntryEvents);
    }

    private void println(String source, Iterable<CacheEntryEvent<? extends K, ? extends V>> cacheEntryEvents) {
        System.out.printf("[Thread : %s] %s - %s\n", Thread.currentThread().getName(), source, cacheEntryEvents);
    }

}
