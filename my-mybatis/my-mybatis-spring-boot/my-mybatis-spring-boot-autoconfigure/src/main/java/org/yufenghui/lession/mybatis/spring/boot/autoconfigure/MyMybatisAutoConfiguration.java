package org.yufenghui.lession.mybatis.spring.boot.autoconfigure;

import cn.yufenghui.lession.mybatis.annotation.EnableMybatis;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Yu Fenghui
 * @date 2021/5/27 9:18
 * @since
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean({DataSource.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableMybatis(
        basePackage = "${my-mybatis.basePackage}",
        configLocation = "${my-mybatis.configLocation}",
        mapperLocation = "${my-mybatis.mapperLocation}"
)
public class MyMybatisAutoConfiguration {

}
