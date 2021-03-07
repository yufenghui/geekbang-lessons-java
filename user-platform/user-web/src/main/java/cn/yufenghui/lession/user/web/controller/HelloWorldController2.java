package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.context.ComponentContext;
import cn.yufenghui.lession.user.service.UserService;
import cn.yufenghui.lession.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 13:41
 * @since
 */
@Path("/hello")
public class HelloWorldController2 implements RestController {

    private UserService userService = ComponentContext.getInstance().getComponent("bean/UserService");

    @GET
    @POST
    @Path("/world2")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return userService.getAll();
    }

}
