package cn.yufenghui.lession.user.web.controller;

import cn.yufenghui.lession.web.mvc.controller.RestController;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 10:38
 * @since
 */
@Path("/config")
public class ConfigInjectController implements RestController {

    @ConfigProperty(name = "application.name", defaultValue = "user-web")
    private String applicationName;

    @ConfigProperty(name = "user.name", defaultValue = "yfh")
    private String userName;

    @ConfigProperty(name = "user.age", defaultValue = "18")
    private int userAge;

    @ConfigProperty(name = "user.age", defaultValue = "18")
    private Integer userAgeInteger;


    @GET
    @Path("/getInjectConfig")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return applicationName + " : " + userName + " - " + userAge + "|" + userAgeInteger;
    }

}
