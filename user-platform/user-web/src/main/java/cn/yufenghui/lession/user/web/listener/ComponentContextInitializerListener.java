package cn.yufenghui.lession.user.web.listener;

import cn.yufenghui.lession.user.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 13:55
 * @since
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext  = sce.getServletContext();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);

        System.out.println("初始化ComponentContext成功。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComponentContext componentContext = ComponentContext.getInstance();
        componentContext.destroy();
    }

}
