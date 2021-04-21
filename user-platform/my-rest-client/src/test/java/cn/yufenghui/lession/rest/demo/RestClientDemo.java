package cn.yufenghui.lession.rest.demo;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Yu Fenghui
 * @date 2021/3/31 14:35
 * @since
 */
public class RestClientDemo {

    @Test
    public void testGet() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }

    @Test
    public void testPost() {

        User user = new User(1L, "yfh");


        Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .post(entity);                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }
}
