package cn.yufenghui.lession.configuration.microprofile.config.jmx;

import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 10:56
 * @since
 */
public class DefaultConfigManager implements DefaultConfigManagerMBean {

    private ConfigProviderResolver configProvider = ConfigProviderResolver.instance();

    @Override
    public Map<String, String> getConfig() {

        Map<String, String> properties = new LinkedHashMap<>();

        Iterable<ConfigSource> configSources = configProvider.getConfig().getConfigSources();

        configSources.forEach(configSource -> {
            properties.putAll(configSource.getProperties());
        });

        return properties;
    }

    @Override
    public Map<String, Map<String, String>> getConfigSource() {

        Map<String, Map<String, String>> properties = new LinkedHashMap<>();

        Iterable<ConfigSource> configSources = configProvider.getConfig().getConfigSources();

        configSources.forEach(configSource -> {
            properties.put(configSource.getName(), configSource.getProperties());
        });

        return properties;
    }

}
