package cn.yufenghui.lession.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.ServiceLoader;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 9:58
 * @since
 */
public class ExampleConfigDemo {

    public static void main(String[] args) {

        ServiceLoader<ConfigProviderResolver> serviceLoader = ServiceLoader.load(ConfigProviderResolver.class);
        ConfigProviderResolver configProviderResolver = serviceLoader.iterator().next();

        if(configProviderResolver != null) {

            Config config = configProviderResolver.getConfig();
            String applicationName = config.getValue("application.name", String.class);
            System.out.println(applicationName);

            Integer userAge = config.getValue("user.age", Integer.class);
            System.out.println(userAge);

        }


    }

}
