package cn.yufenghui.lession.cache;

import cn.yufenghui.lession.cache.configuration.ConfigurationUtils;
import cn.yufenghui.lession.cache.event.TestCacheEntryListener;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * {@link Caching} Test
 *
 * @author Yu Fenghui
 * @date 2021/4/13 17:46
 * @since
 */
public class CachingTest {

    @Test
    public void testSampleInMemory() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager(URI.create("in-memory://localhost/"), null);
        // configure the cache
        MutableConfiguration<String, Integer> config =
                new MutableConfiguration<String, Integer>()
                        .setTypes(String.class, Integer.class);

        // create the cache
        Cache<String, Integer> cache = cacheManager.createCache("simpleCache", config);

        // add listener
        cache.registerCacheEntryListener(ConfigurationUtils.cacheEntryListenerConfiguration(new TestCacheEntryListener<>()));

        // cache operations
        String key = "key";
        Integer value1 = 1;
        cache.put("key", value1);

        // update
        value1 = 2;
        cache.put("key", value1);

        Integer value2 = cache.get(key);
        assertEquals(value1, value2);
        cache.remove(key);
        assertNull(cache.get(key));
    }

    @Test
    public void testSampleRedis() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager(URI.create("redis://127.0.0.1:6379/"), null);
        // configure the cache
        MutableConfiguration<String, Integer> config =
                new MutableConfiguration<String, Integer>()
                        .setTypes(String.class, Integer.class);

        // create the cache
        Cache<String, Integer> cache = cacheManager.createCache("redisCache", config);

        // add listener
        cache.registerCacheEntryListener(ConfigurationUtils.cacheEntryListenerConfiguration(new TestCacheEntryListener<>()));

        // cache operations
        String key = "redis-key";
        Integer value1 = 1;
        cache.put(key, value1);

        // update
        value1 = 2;
        cache.put(key, value1);

        Integer value2 = cache.get(key);
        assertEquals(value1, value2);
        cache.remove(key);
        assertNull(cache.get(key));
    }

    @Test
    public void testJedis() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        try (Jedis jedis = pool.getResource()) {
            /// ... do stuff here ... for example
            jedis.set("foo", "123");
            String foobar = jedis.get("foo");
            System.out.println(foobar);
        }
/// ... when closing your application:
        pool.close();
    }

}
