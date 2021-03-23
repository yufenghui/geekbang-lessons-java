package cn.yufenghui.lession.configuration.microprofile.config.source.servlet;

import cn.yufenghui.lession.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/23 10:37
 * @since
 */
public class ServletContextConfigSource extends MapBasedConfigSource {

    private ServletContext servletContext;

    public ServletContextConfigSource(ServletContext servletContext) {
        super("ConfigSource[ServletContext]", 500);
    }

    @Override
    protected void loadConfigData(Map configData) throws Throwable {
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String parameterName = initParameterNames.nextElement();
            String parameterValue = servletContext.getInitParameter(parameterName);
            configData.put(parameterName, parameterValue);
        }
    }

}
