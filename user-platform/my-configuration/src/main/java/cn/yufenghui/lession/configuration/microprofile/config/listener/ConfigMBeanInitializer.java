package cn.yufenghui.lession.configuration.microprofile.config.listener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 18:27
 * @since
 */
public class ConfigMBeanInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        servletContext.addListener(ConfigMBeanListener.class);
    }

}
