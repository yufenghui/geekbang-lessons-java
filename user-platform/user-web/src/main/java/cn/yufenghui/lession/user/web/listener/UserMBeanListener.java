package cn.yufenghui.lession.user.web.listener;

import cn.yufenghui.lession.user.domain.User;
import cn.yufenghui.lession.user.management.UserManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 18:40
 * @since
 */
public class UserMBeanListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            // 为 UserMXBean 定义 ObjectName
            ObjectName userObjectName = new ObjectName("cn.yufenghui.lesson.user.management:type=User");
            // 创建 UserMBean 实例
            User user = new User();
            mBeanServer.registerMBean(createUserMBean(user), userObjectName);
        } catch (Exception e) {
            System.out.println("UserMBean初始化失败: " + e.getMessage());
        }

        System.out.println("UserMBean初始化成功");
    }

    private Object createUserMBean(User user) throws Exception {
        return new UserManager(user);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
