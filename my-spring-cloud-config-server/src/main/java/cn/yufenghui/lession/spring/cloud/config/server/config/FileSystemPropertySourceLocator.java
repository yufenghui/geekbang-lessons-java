package cn.yufenghui.lession.spring.cloud.config.server.config;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        PropertySource<?> fileSystemPropertySource = null;

        URL resource = null;
        try {
            resource = new ClassPathResource(FILE_LOCATION).getURL();
        } catch (IOException e) {
            System.out.println("配置文件无法找到: " + FILE_LOCATION);
            return fileSystemPropertySource;
        }

        try (InputStreamReader inputStream = new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            Stream<Map.Entry<Object, Object>> stream = properties.entrySet().stream();
            Map<String, Object> source = stream.collect(Collectors.toMap(
                    e -> String.valueOf(e.getKey()),
                    e -> e.getValue()));

            fileSystemPropertySource = new MapPropertySource(PROPERTY_SOURCE_NAME, source);
        } catch (Exception e) {
            System.out.println("配置文件无法找到: " + FILE_LOCATION);
            return fileSystemPropertySource;
        }

        return fileSystemPropertySource;
    }

}
