package cn.yufenghui.lession.cache.redis;

import cn.yufenghui.lession.cache.AbstractCacheManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 11:39
 * @since
 */
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient client;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        client = RedisClient.create(RedisURI.create(uri));
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        ByteArrayCodec redisCodec = new ByteArrayCodec();
        StatefulRedisConnection<byte[], byte[]> connect = client.connect(redisCodec);
        return new LettuceCache(this, cacheName, configuration, connect);
    }

    @Override
    protected void doClose() {
        client.shutdown();
    }

}
