package cn.yufenghui.lession.user.web.listener;

import javax.cache.Cache;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 14:15
 * @since
 */
public class CacheableHttpSessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        Cache<String, Map> cache = getCache(event);

        HttpSession session = event.getSession();
        String sessionId = session.getId();
        if(cache.containsKey(sessionId)) {
            Map<String, Object> attributeMap = cache.get(sessionId);
            attributeMap.put(event.getName(), event.getValue());
        } else {
            Map<String, Object> attributeMap = new HashMap<>();
            attributeMap.put(event.getName(), event.getValue());
            cache.put(sessionId, attributeMap);
        }

        System.out.println(String.format("attributeAdded: %s=%s", event.getName(), event.getValue()));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        Cache<String, Map> cache = getCache(event);

        HttpSession session = event.getSession();
        String sessionId = session.getId();
        if(cache.containsKey(sessionId)) {
            cache.remove(sessionId);
        }

        System.out.println(String.format("attributeRemoved: %s=%s", event.getName(), event.getValue()));
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        Cache<String, Map> cache = getCache(event);

        HttpSession session = event.getSession();
        String sessionId = session.getId();
        if(cache.containsKey(sessionId)) {
            Map<String, Object> attributeMap = cache.get(sessionId);
            attributeMap.put(event.getName(), event.getValue());
        } else {
            Map<String, Object> attributeMap = new HashMap<>();
            attributeMap.put(event.getName(), event.getValue());
            cache.put(sessionId, attributeMap);
        }

        System.out.println(String.format("attributeReplaced: %s=%s", event.getName(), event.getValue()));
    }

    private Cache<String, Map> getCache(HttpSessionBindingEvent event) {
        ServletContext servletContext = event.getSession().getServletContext();
        Cache<String, Map> cache = (Cache) servletContext.getAttribute("CACHE");

        return cache;
    }

}
