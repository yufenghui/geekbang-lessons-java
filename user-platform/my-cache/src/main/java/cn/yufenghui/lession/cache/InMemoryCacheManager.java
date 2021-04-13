package cn.yufenghui.lession.cache;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * @author Yu Fenghui
 * @date 2021/4/12 11:05
 * @since
 */
public class InMemoryCacheManager extends AbstractCacheManager {

    protected InMemoryCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        return null;
    }

}
