package cn.yufenghui.lession.spring.cloud.config.server.util;

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
 * @date 2021/6/3 9:49
 * @since
 */
public class PropertyUtil {

    public static Map<String, Object> loadProperties(String propertiesLocation) {
        Map<String, Object> source = null;
        URL resource = null;

        try {
            resource = new ClassPathResource(propertiesLocation).getURL();
        } catch (IOException e) {
            System.out.println("配置文件无法找到: " + propertiesLocation);
            return source;
        }

        try (InputStreamReader inputStream = new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            Stream<Map.Entry<Object, Object>> stream = properties.entrySet().stream();
            source = stream.collect(Collectors.toMap(
                    e -> String.valueOf(e.getKey()),
                    e -> e.getValue()));

            return source;
        } catch (Exception e) {
            System.out.println("配置文件无法找到: " + propertiesLocation);
        }

        return source;
    }

}
