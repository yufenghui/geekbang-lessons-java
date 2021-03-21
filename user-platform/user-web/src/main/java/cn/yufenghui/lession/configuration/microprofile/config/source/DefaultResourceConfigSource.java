package cn.yufenghui.lession.configuration.microprofile.config.source;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * @author : yufenghui
 * @date : 2021/3/20 21:20
 * @Description:
 */
public class DefaultResourceConfigSource extends MapBasedConfigSource {

    private static final String DEFAULT_CONFIG_FILE_LOCATION = "META-INF/microprofile-config.properties";

    public DefaultResourceConfigSource() {
        super("ConfigSource[Default Resource]", 100);
    }

    @Override
    protected void loadConfigData(Map configData) throws Throwable {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(DEFAULT_CONFIG_FILE_LOCATION);
        if(resource == null) {
            System.out.println("配置文件无法找到: " + DEFAULT_CONFIG_FILE_LOCATION);
            return;
        }

        try(InputStreamReader inputStream = new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            configData.putAll(properties);
        }
    }

}
