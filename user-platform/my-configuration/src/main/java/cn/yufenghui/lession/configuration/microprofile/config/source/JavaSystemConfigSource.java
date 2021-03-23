package cn.yufenghui.lession.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 11:12
 * @since
 */
public class JavaSystemConfigSource extends MapBasedConfigSource {

    public JavaSystemConfigSource() {
        // Java 系统参数
        super("ConfigSource[Java System]", 400);
    }

    @Override
    protected void loadConfigData(Map configData) throws Throwable {
        configData.putAll(System.getProperties());
    }

}
