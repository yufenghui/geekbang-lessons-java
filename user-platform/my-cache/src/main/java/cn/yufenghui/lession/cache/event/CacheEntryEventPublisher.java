package cn.yufenghui.lession.cache.event;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryEvent;

/**
 * @author Yu Fenghui
 * @date 2021/4/12 13:51
 * @since
 */
public class CacheEntryEventPublisher {


    public void registerCacheEntryListener(CacheEntryListenerConfiguration configuration) {

    }

    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration configuration) {

    }

    public <K, V> void publish(CacheEntryEvent<? extends K, ? extends V> event) {
        //
    }

}
