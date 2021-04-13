package cn.yufenghui.lession.cache.integration;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Yu Fenghui
 * @date 2021/4/12 14:15
 * @since
 */
public class CompositeFallbackStorage extends AbstractFallbackStorage<Object, Object> {

    private final ConcurrentHashMap<ClassLoader, List<FallbackStorage>> fallbackStorageCache = new ConcurrentHashMap<>();

    private final List<FallbackStorage> fallbackStorages;

    public CompositeFallbackStorage(ClassLoader classLoader) {
        super(Integer.MIN_VALUE);
        this.fallbackStorages = fallbackStorageCache.computeIfAbsent(classLoader, this::loadFallbackStorages);
    }

    public CompositeFallbackStorage(int priority) {
        this(Thread.currentThread().getContextClassLoader());
    }

    private List<FallbackStorage> loadFallbackStorages(ClassLoader classLoader) {
        return StreamSupport.stream(ServiceLoader.load(FallbackStorage.class, classLoader).spliterator(), false)
                .sorted(PRIORITY_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public Object load(Object key) throws CacheLoaderException {
        Object value = null;
        for(FallbackStorage fallbackStorage : fallbackStorages) {
            value = fallbackStorage.load(key);
            if(value != null) {
                break;
            }
        }

        return value;
    }

    @Override
    public void write(Cache.Entry<?, ?> entry) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.write(entry));
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        fallbackStorages.forEach(fallbackStorage -> fallbackStorage.delete(key));
    }

}
