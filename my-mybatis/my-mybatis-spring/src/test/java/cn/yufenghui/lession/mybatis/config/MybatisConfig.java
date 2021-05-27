package cn.yufenghui.lession.mybatis.config;

import cn.yufenghui.lession.mybatis.annotation.EnableMybatis;
import cn.yufenghui.lession.mybatis.interceptor.TestInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 15:32
 * @since
 */
@EnableMybatis(
        basePackage = "cn.yufenghui.lession.mybatis.mapper",
        configLocation = "classpath:/mybatis-config.xml",
        mapperLocation = "classpath*:/mapper/**/*.xml"
)
@Configuration
public class MybatisConfig {

    @Bean
    public Interceptor testInterceptor() {
        return new TestInterceptor();
    }

}
