package cn.yufenghui.lession.user.web.initializer;

import cn.yufenghui.lession.user.web.listener.DBInitializerListener;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 18:25
 * @since
 */
public class DBListenerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        servletContext.addListener(DBInitializerListener.class);
    }

}
