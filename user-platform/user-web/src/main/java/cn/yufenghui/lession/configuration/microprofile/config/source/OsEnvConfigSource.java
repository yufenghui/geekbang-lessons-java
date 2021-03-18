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
public class OsEnvConfigSource implements ConfigSource {

    private final Map<String, String> properties = new LinkedHashMap<>();

    public OsEnvConfigSource() {
        // OS 环境变量
        Map<String, String> envProperties = System.getenv();
        this.properties.putAll(envProperties);
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
        return "ConfigSource[OS Env]";
    }

    @Override
    public int getOrdinal() {
        return 300;
    }

}
