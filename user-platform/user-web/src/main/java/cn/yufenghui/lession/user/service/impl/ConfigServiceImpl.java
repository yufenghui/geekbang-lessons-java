package cn.yufenghui.lession.user.service.impl;

import cn.yufenghui.lession.user.service.ConfigService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * @author Yu Fenghui
 * @date 2021/3/24 10:14
 * @since
 */
public class ConfigServiceImpl implements ConfigService {

    @ConfigProperty(name = "application.name", defaultValue = "user-web")
    private String applicationName;

    @Override
    public String getInjectConfig() {
        return applicationName;
    }

}
