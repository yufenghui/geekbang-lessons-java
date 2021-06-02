package cn.yufenghui.lession.spring.cloud.config.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yu Fenghui
 * @date 2021/6/2 19:14
 * @since
 */
@Configuration
public class FileSystemConfigBootstrapConfiguration {

    @Bean
    public FileSystemPropertySourceLocator fileSystemPropertySourceLocator() {
        return new FileSystemPropertySourceLocator();
    }

}
