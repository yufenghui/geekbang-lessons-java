package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/4/21 9:52
 * @since
 */
@Path("/")
public class LoginController implements PageController {

    @Path("")
    @GET
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "login_form.jsp";
    }

}
