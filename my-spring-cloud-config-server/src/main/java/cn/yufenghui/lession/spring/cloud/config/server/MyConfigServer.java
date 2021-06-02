package cn.yufenghui.lession.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server 引导类
 *
 * @author Yu Fenghui
 * @date 2021/6/2 18:31
 * @since
 */
@SpringBootApplication
@EnableConfigServer
public class MyConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(MyConfigServer.class, args);
    }

}
