package cn.yufenghui.lession.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Yu Fenghui
 * @date 2021/6/2 19:30
 * @since
 */
@SpringBootApplication
public class MyConfigClient {

    @Value("${my.name}")
    private String myName;

    @Value("${my.age}")
    private int myAge;

    @Value("${my.city}")
    private String myCity;

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.printf("my.name = %s, my.age = %d, my.city = %s %n", myName, myAge, myCity);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyConfigClient.class, args);
    }

}
