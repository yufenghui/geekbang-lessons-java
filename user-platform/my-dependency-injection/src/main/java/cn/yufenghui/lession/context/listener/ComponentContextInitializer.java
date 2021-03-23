package cn.yufenghui.lession.context.listener;

import cn.yufenghui.lession.context.ComponentContext;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 14:32
 * @since
 */
public class ComponentContextInitializer implements ServletContainerInitializer {


    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
//        servletContext.addListener(new ComponentContextListener());
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);
    }

}
