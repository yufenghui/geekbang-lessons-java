package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.user.common.Constants;
import cn.yufenghui.lession.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/4/21 10:20
 * @since
 */
@Path("/gitee")
public class GiteeLoginController implements PageController {


    @Path("/login")
    @GET
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String url = String.format(Constants.GITEE_AUTHORIZATION_URL, Constants.GITEE_CLIENT_ID, Constants.REDIRECT_URI);

        return "redirect:" + url;
    }

}
