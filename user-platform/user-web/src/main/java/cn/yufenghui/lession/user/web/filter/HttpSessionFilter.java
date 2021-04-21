package cn.yufenghui.lession.user.web.filter;

import cn.yufenghui.lession.user.domain.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 14:16
 * @since
 */
public class HttpSessionFilter implements Filter {

    private static final String CACHE_URI_PARAM_NAME = "javax.cache.CacheManager.uri";

    private Cache<String, Map> cache;

    private static final List<String> publicUrl = Arrays.asList("/", "/user/login", "/user/logout", "/gitee/login", "/login_form.jsp");


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        String uri = filterConfig.getInitParameter(CACHE_URI_PARAM_NAME);
        CacheManager cacheManager = cachingProvider.getCacheManager(URI.create(uri), null);
        // configure the cache
        MutableConfiguration<String, Map> config = new MutableConfiguration<String, Map>()
                .setTypes(String.class, Map.class);

        cache = cacheManager.createCache("http-session-cache", config);

        filterConfig.getServletContext().setAttribute("CACHE", cache);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestURI = httpRequest.getRequestURI();
        String servletContextPath = httpRequest.getContextPath();
        String requestMappingPath = StringUtils.substringAfter(requestURI,
                StringUtils.replace(servletContextPath, "//", "/"));


        if(publicUrl.contains(requestMappingPath) || requestMappingPath.startsWith("/static")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession httpSession = httpRequest.getSession();
        String sessionId = httpSession.getId();

        Map<String, Object> attributesMap = cache.get(sessionId);
        if(attributesMap == null) {
            IOUtils.write("need login!", response.getOutputStream(), "UTF-8");
            return;
        }

        User cacheUser = (User) attributesMap.get("user");
        if(cacheUser == null) {
            IOUtils.write("need login!", response.getOutputStream(), "UTF-8");
        } else {
            httpSession.setAttribute("user", cacheUser);
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        cache.close();
    }

}
