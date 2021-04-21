package cn.yufenghui.lession.user.web.controller.user;

import cn.yufenghui.lession.user.domain.User;
import cn.yufenghui.lession.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 15:43
 * @since
 */
@Path("/user")
public class UserLogoutController implements RestController {

    @POST
    @Path("/logout")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        session.removeAttribute("user");

        return "注销成功: " + user;
    }

}
