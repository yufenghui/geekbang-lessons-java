package cn.yufenghui.lession.web.mvc;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 处理方法信息类
 *
 * @author Yu Fenghui
 * @date 2021/3/1 9:18
 * @since
 */
public class HandlerMethodInfo {

    private final String requestPath;

    private final Method handlerMethod;

    private final Set<String> supportHttpMethods;

    public HandlerMethodInfo(String requestPath, Method handlerMethod, Set<String> supportHttpMethods) {
        this.requestPath = requestPath;
        this.handlerMethod = handlerMethod;
        this.supportHttpMethods = supportHttpMethods;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public Set<String> getSupportHttpMethods() {
        return supportHttpMethods;
    }
}
