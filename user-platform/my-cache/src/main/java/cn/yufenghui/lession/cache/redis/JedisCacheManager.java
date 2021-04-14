package cn.yufenghui.lession.cache.redis;

import cn.yufenghui.lession.cache.AbstractCacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 10:45
 * @since
 */
public class JedisCacheManager extends AbstractCacheManager {

    private final JedisPool jedisPool;

    public JedisCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        this.jedisPool = new JedisPool(uri);
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        Jedis jedis = jedisPool.getResource();
        return new JedisCache(this, cacheName, configuration, jedis);
    }

    @Override
    protected void doClose() {
        jedisPool.close();
    }

}
