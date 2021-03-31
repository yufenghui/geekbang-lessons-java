package cn.yufenghui.lession.configuration.microprofile.config.servlet.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @author Yu Fenghui
 * @date 2021/3/30 14:48
 * @since
 */
public class ConfigServletRequestListener implements ServletRequestListener {

    private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();

    public static Config getConfig() {
        return configThreadLocal.get();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletContext servletContext = sre.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();

        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config config = configProviderResolver.getConfig(classLoader);
        configThreadLocal.set(config);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 防止内存泄露 OOM
        configThreadLocal.remove();
    }

}
