package cn.yufenghui.lession.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Yu Fenghui
 * @date 2021/3/22 13:51
 * @since
 */
public class ConfigTest {

    private ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();

    @Before
    public void initConfig() {

    }

    @Test
    public void testOsEnvConfigSource() {
        Config config = configProviderResolver.getConfig();
        String os = config.getValue("OS", String.class);
        Assert.assertTrue("Windows_NT".equalsIgnoreCase(os));
    }

    @Test
    public void testJavaSystemConfigSource() {
        System.setProperty("myJavaSystemProperty", "hello");
        Config config = configProviderResolver.getConfig();

        String myJavaSystemProperty = config.getValue("myJavaSystemProperty", String.class);
        Assert.assertTrue("hello".equalsIgnoreCase(myJavaSystemProperty));
    }

    @Test
    public void testDefaultResourceConfigSource() {
        Config config = configProviderResolver.getConfig();

        String myJavaSystemProperty = config.getValue("application.name", String.class);
        Assert.assertTrue("user-web-demo".equalsIgnoreCase(myJavaSystemProperty));
    }

}
