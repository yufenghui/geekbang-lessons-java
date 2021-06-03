package cn.yufenghui.lession.spring.cloud.config.server.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yu Fenghui
 * @date 2021/6/2 19:14
 * @since
 */
@ConditionalOnClass(EnableConfigServer.class)
@Configuration
public class FileSystemConfigBootstrapConfiguration {

    @Bean
    public FileSystemPropertySourceLocator fileSystemPropertySourceLocator() {
        return new FileSystemPropertySourceLocator();
    }

}
