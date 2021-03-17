package cn.yufenghui.lession.user.management;

import cn.yufenghui.lession.user.domain.User;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author Yu Fenghui
 * @date 2021/3/17 16:17
 * @since
 */
public class UserMXBeanDemo {

    public static void main(String[] args) throws Exception {

        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 为 UserMXBean 定义 ObjectName
        ObjectName objectName = new ObjectName("cn.yufenghui.lesson.user.management:type=User");
        // 创建 UserMBean 实例
        User user = new User();
        mBeanServer.registerMBean(createUserMBean(user), objectName);

        while (true) {
            Thread.sleep(2000);
            System.out.println(user);
        }

    }

    private static Object createUserMBean(User user) throws Exception {
        return new UserManager(user);
    }

}
