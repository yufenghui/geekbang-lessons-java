package cn.yufenghui.lession.mybatis.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活 Mybatis
 *
 * @author Yu Fenghui
 * @date 2021/5/11 15:08
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(MyBatisBeanDefinitionRegistrar.class)
public @interface EnableMybatis {

    /**
     * the Mapper inerface packages
     *
     * @return
     */
    String basePackage();

    /**
     * the location of Mybatis Config File {@link org.apache.ibatis.session.Configuration}
     *
     * @return config file location
     */
    String configLocation() default "classpath*:/mybatis-config.xml";

    /**
     * the location of {@link Mapper} xml
     *
     * @return
     */
    String mapperLocation();

}
