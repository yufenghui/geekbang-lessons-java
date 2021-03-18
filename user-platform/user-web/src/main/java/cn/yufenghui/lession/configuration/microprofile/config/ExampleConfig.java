package cn.yufenghui.lession.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Yu Fenghui
 * @date 2021/3/17 17:03
 * @since
 */
public class ExampleConfig implements Config {

    private List<ConfigSource> configSources = new LinkedList<>();

    private Map<String, Converter> converterMap = new HashMap<>();

    private static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
        @Override
        public int compare(ConfigSource o1, ConfigSource o2) {
            return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
        }
    };


    public ExampleConfig() {
        // 初始化ConfigSource
        ClassLoader classLoader = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);

        serviceLoader.forEach(configSources::add);
        // 排序
        configSources.sort(configSourceComparator);

        // 初始化Converter
        ServiceLoader<Converter> converterServiceLoader = ServiceLoader.load(Converter.class, classLoader);
        converterServiceLoader.forEach(converter -> {
            Type[] types = converter.getClass().getGenericInterfaces();
            ParameterizedType parameterizedType = (ParameterizedType) types[0];
            Type type = parameterizedType.getActualTypeArguments()[0];

            String converterClassName = type.getTypeName();

            converterMap.put(converterClassName, converter);
        });
    }


    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);

        if(propertyType == String.class) {
            return (T) propertyValue;
        }
        // 类型转换
        Converter<T> converter = getConverter(propertyType).get();
        if(converter == null) {
            return null;
        }

        return converter.convert(propertyValue);
    }

    protected String getPropertyValue(String propertyName) {
        String propertyValue = null;
        for (ConfigSource configSource : configSources) {
            propertyValue = configSource.getValue(propertyName);
            if(propertyValue != null) {
                break;
            }
        }

        return propertyValue;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return Collections.unmodifiableList(configSources);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.ofNullable(converterMap.get(forType.getTypeName()));
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }

}
