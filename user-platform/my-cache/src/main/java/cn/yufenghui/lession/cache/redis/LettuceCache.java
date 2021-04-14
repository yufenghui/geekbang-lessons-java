package cn.yufenghui.lession.cache.redis;

import cn.yufenghui.lession.cache.AbstractCache;
import cn.yufenghui.lession.cache.ExpirableEntry;
import cn.yufenghui.lession.cache.serialization.DefaultSerialization;
import cn.yufenghui.lession.cache.serialization.Serialization;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 11:40
 * @since
 */
public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final StatefulRedisConnection<byte[], byte[]> connection;

    private final Serialization serialization = new DefaultSerialization();

    private final RedisCommands<byte[], byte[]> command;

    public LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, StatefulRedisConnection<byte[], byte[]> connection) {
        super(cacheManager, cacheName, configuration);
        this.connection = connection;
        command = connection.sync();
    }


    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);
        return command.exists(keyBytes) > 0;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);
        return this.getEntry(keyBytes);
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
        byte[] valueBytes = command.get(keyBytes);
        return ExpirableEntry.of(serialization.deserialize(keyBytes), serialization.deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(entry.getKey());
        byte[] valueBytes = serialization.serialize(entry.getValue());
        command.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialization.serialize(key);

        ExpirableEntry<K, V> entry = getEntry(keyBytes);
        command.del(keyBytes);

        return entry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        command.flushdb();
    }

    @Override
    protected Set<K> keySet() {
        List<byte[]> keys = command.keys("*".getBytes(StandardCharsets.UTF_8));
        return keys.stream()
                .map(key -> (K) serialization.deserialize(key))
                .collect(Collectors.toSet());
    }

    @Override
    protected void doClose() {
        connection.close();
    }

}
