package cn.yufenghui.lession.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Yu Fenghui
 * @date 2021/3/17 17:05
 * @since
 */
public class LocalFileConfigSource implements ConfigSource {

    private final Map<String, String> properties = new LinkedHashMap<>();

    public LocalFileConfigSource() {
        // 添加 config.properties
        InputStreamReader reader= new InputStreamReader(this.getClass().getResourceAsStream("/config.properties"), StandardCharsets.UTF_8);

        Properties p = new Properties();
        try {
            p.load(reader);

            p.forEach((key, value) -> {
                String strKey = (String) key;
                String strValue = (String) value;

                this.properties.put(strKey, strValue);
            });

        } catch (IOException e) {
            System.out.println("加载config.propertie失败");
        }
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
        return "ConfigSource[Local File]";
    }

    @Override
    public int getOrdinal() {
        return 1000;
    }

}
