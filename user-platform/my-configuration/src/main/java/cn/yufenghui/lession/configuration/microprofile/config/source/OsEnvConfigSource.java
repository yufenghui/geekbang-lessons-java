package cn.yufenghui.lession.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 11:12
 * @since
 */
public class OsEnvConfigSource extends MapBasedConfigSource {

    public OsEnvConfigSource() {
        // OS 环境变量
        super("ConfigSource[OS Env]", 300);
    }

    @Override
    protected void loadConfigData(Map configData) throws Throwable {
        configData.putAll(System.getenv());
    }

}
