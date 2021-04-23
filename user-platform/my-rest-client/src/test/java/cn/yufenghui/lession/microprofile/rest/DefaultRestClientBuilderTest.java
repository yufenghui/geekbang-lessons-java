package cn.yufenghui.lession.microprofile.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.net.URL;

/**
 * @author Yu Fenghui
 * @date 2021/4/23 14:09
 * @since
 */
public class DefaultRestClientBuilderTest {

    public static void main(String[] args) throws Exception {

        HelloWorldService helloWorldService = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://127.0.0.1:8080"))
                .build(HelloWorldService.class);


        System.out.println(helloWorldService.helloWorld("yfh", "Hello World"));
    }

}


@Path("/hello")
interface HelloWorldService {

    @GET
    @Path("/world")
    String helloWorld(@QueryParam("name") String name, @QueryParam("message") String message);

}