package cn.yufenghui.lession.configuration.microprofile.config.jmx;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 10:56
 * @since
 */
public interface ExampleConfigManagerMBean {

    Map<String, Map<String, String>> getConfigSource();

    Map<String, String> getConfig();

}
