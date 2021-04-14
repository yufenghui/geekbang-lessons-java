package cn.yufenghui.lession.cache.redis;

import cn.yufenghui.lession.cache.AbstractCache;
import cn.yufenghui.lession.cache.ExpirableEntry;
import cn.yufenghui.lession.cache.serialization.DefaultSerialization;
import cn.yufenghui.lession.cache.serialization.Serialization;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 10:47
 * @since
 */
public class JedisCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final Jedis jedis;

    private final Serialization serialization = new DefaultSerialization();


    protected JedisCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, Jedis jedis) {
        super(cacheManager, cacheName, configuration);
        this.jedis = jedis;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);
        return jedis.exists(keyBytes);
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);
        return getEntry(keyBytes);
    }

    /**
     * 复用，可节省序列化
     *
     * @param keyBytes
     * @return
     * @throws CacheException
     * @throws ClassCastException
     */
    private ExpirableEntry<K,V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        byte[] valueBytes = jedis.get(keyBytes);
        return ExpirableEntry.of(serialization.deserialize(keyBytes), serialization.deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(entry.getKey());
        byte[] valueBytes = serialization.serialize(entry.getValue());
        jedis.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);

        ExpirableEntry<K, V> entry = getEntry(keyBytes);
        jedis.del(keyBytes);

        return entry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        jedis.flushDB();
    }

    @Override
    protected Set<K> keySet() {
        Set<byte[]> keys = jedis.keys("*".getBytes(StandardCharsets.UTF_8));
        return keys.stream()
                .map(key -> (K) serialization.deserialize(key))
                .collect(Collectors.toSet());
    }

    @Override
    protected void doClose() {
        jedis.close();
    }


}
