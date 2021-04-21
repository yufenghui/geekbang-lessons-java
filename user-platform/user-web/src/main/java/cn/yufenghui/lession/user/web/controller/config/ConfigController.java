package cn.yufenghui.lession.user.web.controller.config;

import cn.yufenghui.lession.web.mvc.controller.RestController;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

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
public class ConfigController implements RestController {

    private static final ConfigProviderResolver configProvider = ConfigProviderResolver.instance();


    @GET
    @Path("/getConfig")
    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String configName = request.getParameter("name");

        String configValue = configProvider.getConfig().getValue(configName, String.class);

        return configValue;
    }

}
