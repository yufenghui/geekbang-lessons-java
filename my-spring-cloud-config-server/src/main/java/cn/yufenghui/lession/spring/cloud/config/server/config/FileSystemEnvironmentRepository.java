package cn.yufenghui.lession.spring.cloud.config.server.config;

import cn.yufenghui.lession.spring.cloud.config.server.util.PropertyUtil;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/6/3 9:34
 * @since
 */
public class FileSystemEnvironmentRepository implements EnvironmentRepository {

    private static final String PROPERTY_SOURCE_NAME = "fileSystemPropertySource";
    private static final String FILE_LOCATION = "META-INF/config/default.properties";


    @Override
    public Environment findOne(String application, String profile, String label) {
        if (StringUtils.isEmpty(profile)) {
            profile = "default";
        }

        Environment environment = new Environment(application, profile, label, null, null);

        Map<String, Object> properties = PropertyUtil.loadProperties(FILE_LOCATION);
        PropertySource propertySource = new PropertySource(PROPERTY_SOURCE_NAME, properties);

        environment.add(propertySource);
        return environment;
    }

}
