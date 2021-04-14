package cn.yufenghui.lession.cache;

import cn.yufenghui.lession.cache.configuration.ConfigurationUtils;
import cn.yufenghui.lession.cache.event.TestCacheEntryListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.*;

/**
 * {@link AbstractCache} Test cases
 *
 * @author Yu Fenghui
 * @date 2021/4/13 17:47
 * @since
 */
public class AbstractCacheTest {

    Cache<String, Integer> cache;

    Configuration<String, Integer> configuration;

    @Before
    public void init() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>()
                .setTypes(String.class, Integer.class)
                .addCacheEntryListenerConfiguration(ConfigurationUtils.cacheEntryListenerConfiguration(new TestCacheEntryListener<>()));

        // create cache
        this.cache = cacheManager.createCache("testCache", config);
        this.configuration = config;
    }

    @After
    public void clearCache() {
        cache.removeAll();
    }

    /**
     * Test Meta-data methods :
     * <ul>
     *     <li>{@link Cache#getName()}</li>
     *     <li>{@link Cache#getCacheManager()}</li>
     *     <li>{@link Cache#getConfiguration(Class)}</li>
     * </ul>
     */
    @Test
    public void testGetMetadata() {
        assertEquals("testCache", cache.getName());
        assertEquals(Caching.getCachingProvider().getCacheManager(), cache.getCacheManager());
        assertEquals(ConfigurationUtils.immutableConfiguration(configuration), cache.getConfiguration(Configuration.class));
    }

    @Test
    public void testBasicOps() {

        String key = "test-key";
        Integer value = 1;

        // test containsKey
        assertFalse(cache.containsKey(key));

        // test put if create Cache.Entry
        cache.put(key, value);
        assertTrue(cache.containsKey(key));

        // test get
        assertEquals(value, cache.get(key));

        // test getAll
        assertEquals(singletonMap(key, value), cache.getAll(singleton(key)));

        // test getAndPut
        assertEquals(Integer.valueOf(2), cache.getAndPut(key, 2));

        // test getAndRemove
        assertEquals(Integer.valueOf(2), cache.getAndRemove(key));

        // test putIfAbsent
        assertTrue(cache.putIfAbsent(key, value));
        assertFalse(cache.putIfAbsent(key, value));


        // test replace
        value = 2;
        assertTrue(cache.replace(key, 1, value));
        assertTrue(cache.replace(key, value));
    }

}
