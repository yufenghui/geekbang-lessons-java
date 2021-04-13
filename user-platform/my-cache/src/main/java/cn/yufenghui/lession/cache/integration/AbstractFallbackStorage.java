package cn.yufenghui.lession.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/4/12 14:15
 * @since
 */
public abstract class AbstractFallbackStorage<K, V> implements FallbackStorage<K, V> {

    private final int priority;

    public AbstractFallbackStorage(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
        Map<K, V> map = new LinkedHashMap<>();
        for(K key : keys) {
            map.put(key, load(key));
        }
        return map;
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
        entries.forEach(this::write);
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        keys.forEach(this::delete);
    }

}
