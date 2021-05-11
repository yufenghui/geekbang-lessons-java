package cn.yufenghui.lession.spring.security;

import cn.yufenghui.lession.spring.security.api.MyWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 17:56
 * @since
 */
@Configuration
public class CompositeWebSecurityConfigrer extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<MyWebSecurityConfigurer> webSecurityConfigurers;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.webSecurityConfigurers.forEach(c -> {
            try {
                c.configure(http);
            } catch (Exception e) {
                // do nothing
            }
        });
    }

}
