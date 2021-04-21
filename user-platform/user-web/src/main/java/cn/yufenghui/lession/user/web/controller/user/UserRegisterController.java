package cn.yufenghui.lession.user.web.controller.user;

import cn.yufenghui.lession.context.ComponentContext;
import cn.yufenghui.lession.user.domain.User;
import cn.yufenghui.lession.user.service.UserService;
import cn.yufenghui.lession.web.mvc.controller.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Set;

/**
 * @author : yufenghui
 * @date : 2021/3/7 21:40
 * @Description:
 */
@Path("/user")
public class UserRegisterController implements RestController {

    private UserService userService =
            ComponentContext.getInstance().getComponent("bean/UserService");

    private Validator validator =
            ComponentContext.getInstance().getComponent("bean/validator");


    @GET
    @POST
    @Path("/register")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        User user = new User();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        user.setName(name == null ? "小马哥" : name);
        user.setPassword(password == null ? "****" : password);
        user.setEmail(email == null ? "mercyblitz@gmail.com" : email);
        user.setPhoneNumber(phoneNumber == null ? "123456789" : phoneNumber);

        // 校验结果
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        violations.forEach(c -> {
            System.out.println(c.getMessage());
        });

        if(violations.size() > 0) {
            return "检验失败: " + violations;
        }

        boolean ret = userService.register(user);
        if(ret) {
            return "注册成功：" + user;
        } else {
            return "注册失败";
        }
    }

}
