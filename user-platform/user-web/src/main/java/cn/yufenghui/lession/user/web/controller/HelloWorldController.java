package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.web.mvc.controller.PageController;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 12:37
 * @since
 */
@Path("/hello")
public class HelloWorldController implements PageController {

    @GET
    @POST
    @Path("/world")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        ServletInputStream inputStream = request.getInputStream();

        String contentType = request.getHeader("Content-Type");
        System.out.println("Content-Type: " + contentType);

        String requestBody = IOUtils.toString(inputStream, "UTF-8");
        System.out.println("request body: " + requestBody);

        return "index.jsp";
    }

}
