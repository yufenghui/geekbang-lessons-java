package cn.yufenghui.lession.spring.security.api;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 17:36
 * @since
 */
public interface MyWebSecurityConfigurer {

    void configure(HttpSecurity http) throws Exception;

}
