package cn.yufenghui.lession.configuration.microprofile.config.listener;

import cn.yufenghui.lession.configuration.microprofile.config.jmx.DefaultConfigManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * @author Yu Fenghui
 * @date 2021/3/17 16:49
 * @since
 */
public class ConfigMBeanListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName configObjectName = new ObjectName("cn.yufenghui.lesson.config:type=Config");
            mBeanServer.registerMBean(new DefaultConfigManager(), configObjectName);

        } catch (Exception e) {
            System.out.println("ConfigMBean初始化失败: " + e.getMessage());
        }

        System.out.println("ConfigMBean初始化成功");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
