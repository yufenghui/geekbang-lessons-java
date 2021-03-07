package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.context.ComponentContext;
import cn.yufenghui.lession.user.domain.User;
import cn.yufenghui.lession.user.service.UserService;
import cn.yufenghui.lession.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author : yufenghui
 * @date : 2021/3/7 21:40
 * @Description:
 */
@Path("/user")
public class UserRegisterController implements RestController {

    private UserService userService = ComponentContext.getInstance().getComponent("bean/UserService");

    @GET
    @POST
    @Path("/register")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        User user = new User();
        user.setName("小马哥");
        user.setPassword("******");
        user.setEmail("mercyblitz@gmail.com");
        user.setPhoneNumber("123456789");

        boolean ret = userService.register(user);
        if(ret) {
            return "注册成功：" + user;
        } else {
            return "注册失败";
        }
    }

}
