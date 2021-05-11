package cn.yufenghui.lession.spring.security.config;

import cn.yufenghui.lession.spring.security.api.MyWebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 17:39
 * @since
 */
@Component
public class SecurityConfig2 implements MyWebSecurityConfigurer {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin();
    }

}
