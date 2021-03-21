package cn.yufenghui.lession.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Comparator;

/**
 * @author : yufenghui
 * @date : 2021/3/21 20:41
 * @Description:
 */
public class ConfigSourceOrdinalComparator implements Comparator<ConfigSource> {


    public static final ConfigSourceOrdinalComparator INSTANCE = new ConfigSourceOrdinalComparator();

    @Override
    public int compare(ConfigSource o1, ConfigSource o2) {
        return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
    }

}
