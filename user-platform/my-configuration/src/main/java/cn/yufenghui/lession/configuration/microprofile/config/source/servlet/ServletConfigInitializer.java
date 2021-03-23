package cn.yufenghui.lession.configuration.microprofile.config.source.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 14:13
 * @since
 */
public class ServletConfigInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        servletContext.addListener(ServletContextConfigSourceListener.class);
    }

}
