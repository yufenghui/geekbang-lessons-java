package cn.yufenghui.lession.cache.event;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Factory;
import javax.cache.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * The adapter of {@link ConditionalCacheEntryEventListener} based on {@link CacheEntryListenerConfiguration}
 *
 * @author Yu Fenghui
 * @date 2021/4/14 8:52
 * @since
 */
public class CacheEntryEventListenerAdapter<K, V> implements ConditionalCacheEntryEventListener<K, V> {

    private static List<Object> eventTypesAndHandleMethodNames = Arrays.asList(
            EventType.CREATED, "onCreated",
            EventType.UPDATED, "onUpdated",
            EventType.EXPIRED, "onExpired",
            EventType.REMOVED, "onRemoved"
    );

    private final CacheEntryListenerConfiguration<K, V> configuration;

    private final CacheEntryEventFilter<? super K, ? super V> cacheEntryEventFilter;

    private final CacheEntryListener<? super K, ? super V> cacheEntryListener;

    private final Map<EventType, Method> eventTypeMethods;

    private final Executor executor;

    public CacheEntryEventListenerAdapter(CacheEntryListenerConfiguration<K, V> configuration) {
        this.configuration = configuration;
        this.cacheEntryEventFilter = getCacheEntryEventFilter(configuration);
        this.cacheEntryListener = configuration.getCacheEntryListenerFactory().create();
        this.eventTypeMethods = determineEventTypeMethods(cacheEntryListener);
        this.executor = getExecutor(configuration);
    }

    @Override
    public boolean supports(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException {
        return supportsEventType(event) && cacheEntryEventFilter.evaluate(event);
    }

    private boolean supportsEventType(CacheEntryEvent<? extends K, ? extends V> event) {
        return getSupportedEventTypes().contains(event.getEventType());
    }

    @Override
    public void onEvent(CacheEntryEvent<? extends K, ? extends V> event) {
        if(!supports(event)) {
            return;
        }

        EventType eventType = event.getEventType();
        Method handleMethod = eventTypeMethods.get(eventType);

        executor.execute(() -> {
            try {
                handleMethod.invoke(cacheEntryListener, Collections.singleton(event));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new CacheEntryListenerException(e);
            }
        });
    }

    @Override
    public Set<EventType> getSupportedEventTypes() {
        return eventTypeMethods.keySet();
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public int hashCode() {
        return configuration.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CacheEntryEventListenerAdapter)) {
            return false;
        }

        CacheEntryEventListenerAdapter another = (CacheEntryEventListenerAdapter) obj;
        return this.configuration.equals(another.configuration);
    }

    private Map<EventType, Method> determineEventTypeMethods(CacheEntryListener<? super K,? super V> cacheEntryListener) {
        Map<EventType, Method> eventTypeMethods = new HashMap<>(EventType.values().length);

        Class<?> cacheEntryListenerClass = cacheEntryListener.getClass();

        for(int i = 0; i < eventTypesAndHandleMethodNames.size();) {
            EventType eventType = (EventType) eventTypesAndHandleMethodNames.get(i++);
            String handleMethodName = (String) eventTypesAndHandleMethodNames.get(i++);
            try {
                Method handleMethod = cacheEntryListenerClass.getMethod(handleMethodName, Iterable.class);
                if(handleMethod != null) {
                    eventTypeMethods.put(eventType, handleMethod);
                }
            } catch (NoSuchMethodException ignored) {

            }

        }


        return Collections.unmodifiableMap(eventTypeMethods);
    }

    private CacheEntryEventFilter<? super K,? super V> getCacheEntryEventFilter(CacheEntryListenerConfiguration<K,V> configuration) {
        Factory<CacheEntryEventFilter<? super K, ? super V>> factory = configuration.getCacheEntryEventFilterFactory();
        CacheEntryEventFilter<? super K, ? super V> filter = null;

        if(factory != null) {
            filter = factory.create();
        }

        if(filter == null) {
            // When null no filtering is applied and all appropriate events are notified.
            filter = e -> true;
        }

        return filter;
    }

    private Executor getExecutor(CacheEntryListenerConfiguration<K,V> configuration) {
        Executor executor = null;
        if(configuration.isSynchronous()) {
            executor = Runnable::run;
        } else {
            executor = ForkJoinPool.commonPool();
        }

        return executor;
    }

}
