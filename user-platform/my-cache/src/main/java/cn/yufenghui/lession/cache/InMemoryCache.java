package cn.yufenghui.lession.cache;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * In Memory no-thread-safe {@link Cache}
 *
 * @author Yu Fenghui
 * @date 2021/4/13 17:23
 * @since
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V> {

    private final Map<K, ExpirableEntry<K, V>> cache;

    protected InMemoryCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration) {
        super(cacheManager, cacheName, configuration);
        this.cache = new HashMap<>();
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return cache.containsKey(key);
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        return cache.get(key);
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        K key = entry.getKey();
        cache.put(key, entry);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        return cache.remove(key);
    }

    @Override
    protected void clearEntries() throws CacheException {
        cache.clear();
    }

    @Override
    protected Set<K> keySet() {
        return cache.keySet();
    }

}
