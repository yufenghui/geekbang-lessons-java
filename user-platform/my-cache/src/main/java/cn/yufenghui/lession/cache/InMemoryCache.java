package cn.yufenghui.lession.cache;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/4/13 17:23
 * @since
 */
public class InMemoryCache<K, V> extends AbstractCache<K, V> {

    protected InMemoryCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration) {
        super(cacheManager, cacheName, configuration);
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return false;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        return null;
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {

    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        return null;
    }

    @Override
    protected void clearEntries() throws CacheException {

    }

    @Override
    protected Set<K> keySet() {
        return null;
    }

}
