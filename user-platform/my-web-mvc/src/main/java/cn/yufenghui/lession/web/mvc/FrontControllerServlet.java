package cn.yufenghui.lession.web.mvc;

import cn.yufenghui.lession.web.mvc.controller.Controller;
import cn.yufenghui.lession.web.mvc.controller.PageController;
import cn.yufenghui.lession.web.mvc.controller.RestController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 9:16
 * @since
 */
public class FrontControllerServlet extends HttpServlet {

    /**
     * 请求与Controller实例映射
     */
    private Map<String, Controller> controllerMapping = new HashMap<>();

    /**
     * 请求与Controller方法映射
     */
    private Map<String, HandlerMethodInfo> handlerMethodInfoMapping = new HashMap<>();

    /**
     * 初始化 Servlet
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        initHandlerMethods();
    }

    /**
     * 读取所有的RestController 以及 注解元信息 @Path
     * SPI
     */
    private void initHandlerMethods() {

        for (Controller controller : ServiceLoader.load(Controller.class)) {
            Class<?> controllerClass = controller.getClass();
            Path pathfromClass = controllerClass.getAnnotation(Path.class);

            String requestPath = pathfromClass.value();
            Method[] publicMethods = controllerClass.getMethods();

            for (Method method : publicMethods) {
                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                Path pathFromMethod = method.getAnnotation(Path.class);
                if (pathFromMethod != null) {
                    requestPath += pathFromMethod.value();
                }

                handlerMethodInfoMapping.put(requestPath, new HandlerMethodInfo(requestPath, method, supportedHttpMethods));
            }

            controllerMapping.put(requestPath, controller);
        }

    }

    /**
     * 获取处理方法标注的 HTTP 方法集合
     *
     * @param method
     * @return
     */
    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();

        for (Annotation annotation : method.getAnnotations()) {
            HttpMethod httpMethod = annotation.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }

        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(Arrays.asList(HttpMethod.GET, HttpMethod.POST,
                    HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }

        return supportedHttpMethods;
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        String servletContextPath = request.getContextPath();

        String requestMappingPath = StringUtils.substringAfter(requestURI,
                StringUtils.replace(servletContextPath, "//", "/"));

        Controller controller = controllerMapping.get(requestMappingPath);

        if (controller == null) {
            return;
        }

        HandlerMethodInfo handlerMethodInfo = handlerMethodInfoMapping.get(requestMappingPath);
        if (handlerMethodInfo == null) {
            return;
        }

        try {
            String httpMethod = request.getMethod();
            if (!handlerMethodInfo.getSupportHttpMethods().contains(httpMethod)) {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }

            if (controller instanceof PageController) {
                PageController pageController = PageController.class.cast(controller);
                String viewPath = pageController.execute(request, response);
                if (!viewPath.startsWith("/")) {
                    viewPath = "/" + viewPath;
                }

                ServletContext servletContext = request.getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
                requestDispatcher.forward(request, response);
            } else if (controller instanceof RestController) {
                RestController restController = RestController.class.cast(controller);
                Object returnObj = restController.execute(request, response);

                ServletOutputStream outputStream = response.getOutputStream();
                IOUtils.write(JSON.toJSONBytes(returnObj), outputStream);
            }

        } catch (Throwable t) {
            if (t.getCause() instanceof IOException) {
                throw (IOException) t.getCause();
            } else {
                throw new ServletException((t.getCause()));
            }
        }

    }

}
