package cn.yufenghui.lession.spring.security.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 18:10
 * @since
 */
@Component
public class WebSecurityConfigurerAdapterCheckListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, WebSecurityConfigurerAdapter> beans = applicationContext.getBeansOfType(WebSecurityConfigurerAdapter.class);
        if (beans.size() > 1) {
            System.err.println(String.format("系统中存在 %s个 WebSecurityConfigurerAdapter 实例。", beans.size()));
        }
    }

}
