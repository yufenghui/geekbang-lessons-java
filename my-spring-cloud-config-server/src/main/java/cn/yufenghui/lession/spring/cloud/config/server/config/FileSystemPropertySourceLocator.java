package cn.yufenghui.lession.spring.cloud.config.server.config;

import cn.yufenghui.lession.spring.cloud.config.server.util.PropertyUtil;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/6/2 18:40
 * @since
 */
public class FileSystemPropertySourceLocator implements PropertySourceLocator {

    private static final String PROPERTY_SOURCE_NAME = "fileSystemPropertySource";

    private static final String FILE_LOCATION = "META-INF/config/default.properties";


    @Override
    public PropertySource<?> locate(Environment environment) {
        Map<String, Object> properties = PropertyUtil.loadProperties(FILE_LOCATION);

        PropertySource<?> fileSystemPropertySource = new MapPropertySource(PROPERTY_SOURCE_NAME, properties);

        return fileSystemPropertySource;
    }

}
