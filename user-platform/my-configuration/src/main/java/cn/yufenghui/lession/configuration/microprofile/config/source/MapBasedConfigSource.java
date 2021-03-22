package cn.yufenghui.lession.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 此处的抽象-值得体会
 *
 * @author : yufenghui
 * @date : 2021/3/20 20:59
 * @Description:
 */
public abstract class MapBasedConfigSource implements ConfigSource {

    private String name;

    private int ordinal;

    private final Map<String, String> source;

    public MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.source = getProperties();
    }

    @Override
    public final Map<String, String> getProperties() {
        Map<String, String> configData = new HashMap<>();
        try {
            loadConfigData(configData);
        } catch (Throwable t) {
            throw new IllegalStateException("准备配置数据发生错误", t);
        }

        return Collections.unmodifiableMap(configData);
    }

    /**
     * 加载配置数据
     *
     * @param configData
     * @throws Throwable
     */
    protected abstract void loadConfigData(Map configData) throws Throwable;

    @Override
    public Set<String> getPropertyNames() {
        return source.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return source.get(propertyName);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }
}
