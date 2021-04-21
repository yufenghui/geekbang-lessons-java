package cn.yufenghui.lession.user.web.controller.config;

import cn.yufenghui.lession.context.ComponentContext;
import cn.yufenghui.lession.user.service.ConfigService;
import cn.yufenghui.lession.web.mvc.controller.RestController;

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
public class ConfigServiceInjectController implements RestController {

    private static final ConfigService configService = ComponentContext.getInstance().getComponent("bean/ConfigService");

    @GET
    @Path("/getServiceInjectConfig")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return configService.getInjectConfig();
    }

}
