package cn.yufenghui.lession.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 11:12
 * @since
 */
public class JavaSystemConfigSource implements ConfigSource {

    private final Map<String, String> properties = new LinkedHashMap<>();

    public JavaSystemConfigSource() {
        // Java 系统参数
        Map systemProperties = System.getProperties();
        this.properties.putAll(systemProperties);
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "ConfigSource[Java System]";
    }

    @Override
    public int getOrdinal() {
        return 400;
    }

}
