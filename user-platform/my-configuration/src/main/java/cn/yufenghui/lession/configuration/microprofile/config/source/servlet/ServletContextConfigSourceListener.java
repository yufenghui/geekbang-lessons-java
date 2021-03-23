package cn.yufenghui.lession.configuration.microprofile.config.source.servlet;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 14:04
 * @since
 */
public class ServletContextConfigSourceListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ServletContextConfigSource servletContextConfigSource = new ServletContextConfigSource(servletContext);

        // 获取当前ClassLoader
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        ConfigBuilder configBuilder = configProviderResolver.getBuilder();
        // 配置ClassLoader
        configBuilder.forClassLoader(classLoader);

        // 加载默认配置源（内建的，静态的）
        configBuilder.addDefaultSources();
        // 通过SPI发现配置源（动态的）
        configBuilder.addDiscoveredSources();
        // 通过API扩展配置源（Servlet）
        configBuilder.withSources(servletContextConfigSource);

        // 通过SPI发现 类型转换器
        configBuilder.addDiscoveredConverters();

        // 获取Config
        Config config = configBuilder.build();
        // 注册Config到当前的ClassLoader
        configProviderResolver.registerConfig(config, classLoader);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
