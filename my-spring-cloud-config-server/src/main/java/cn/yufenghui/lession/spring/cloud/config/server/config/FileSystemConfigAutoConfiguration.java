package cn.yufenghui.lession.spring.cloud.config.server.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.config.CompositeConfiguration;
import org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentProperties;
import org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepository;
import org.springframework.cloud.config.server.environment.MultipleJGitEnvironmentRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yu Fenghui
 * @date 2021/6/3 10:19
 * @since
 */
@ConditionalOnClass(EnableConfigServer.class)
@AutoConfigureBefore(CompositeConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class FileSystemConfigAutoConfiguration {

    @Bean
    public FileSystemEnvironmentRepository fileSystemEnvironmentRepository() {
        return new FileSystemEnvironmentRepository();
    }

    @ConditionalOnProperty(value = "spring.cloud.config.server.git.uri")
    @Bean
    public MultipleJGitEnvironmentRepository defaultEnvironmentRepository(
            MultipleJGitEnvironmentRepositoryFactory gitEnvironmentRepositoryFactory,
            MultipleJGitEnvironmentProperties environmentProperties) throws Exception {
        return gitEnvironmentRepositoryFactory.build(environmentProperties);
    }

}
