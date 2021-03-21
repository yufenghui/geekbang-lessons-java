package cn.yufenghui.lession.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 9:58
 * @since
 */
public class DefaultConfigDemo {

    public static void main(String[] args) {

        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();

        if(configProviderResolver != null) {

            Config config = configProviderResolver.getConfig();
            String applicationName = config.getValue("application.name", String.class);
            System.out.println(applicationName);

            Integer userAge = config.getValue("user.age", Integer.class);
            System.out.println(userAge);

        }

    }

}
