package cn.yufenghui.lession.user.web.controller.user;

import cn.yufenghui.lession.user.common.Constants;
import cn.yufenghui.lession.user.domain.User;
import cn.yufenghui.lession.web.mvc.controller.PageController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 15:43
 * @since
 */
@Path("/user")
public class UserLoginController implements PageController {

    @POST
    @Path("/login")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        User user = new User();

        String code = request.getParameter("code");
        if(StringUtils.isNotBlank(code)) {
            // 根据code获取access_token
            String url = String.format(Constants.GITEE_ACCESS_TOKEN_URL,
                    code, Constants.GITEE_CLIENT_ID, Constants.REDIRECT_URI, Constants.GITEE_CLIENT_SECRET);


            Client client = ClientBuilder.newClient();
            Response resp = client
                    .target(url)
                    .request()
                    .post(null);

            String tokenResponseStr = resp.readEntity(String.class);
            JSONObject jsonObject = JSON.parseObject(tokenResponseStr);
            String accessToken = jsonObject.getString("access_token");

            // 获取用户信息
            String userInfoUrl = Constants.GITEE_USER_INFO_URL + "?access_token=" + accessToken;
            Response userInfoResponse = client
                    .target(userInfoUrl)
                    .request()
                    .get();

            String userInfoResponseStr = userInfoResponse.readEntity(String.class);
            JSONObject userInfoObject = JSON.parseObject(userInfoResponseStr);


            user.setId(userInfoObject.getLong("id"));
            user.setName(userInfoObject.getString("name"));

            session.setAttribute("user", user);
        }

        return "redirect:/index";
    }

}
