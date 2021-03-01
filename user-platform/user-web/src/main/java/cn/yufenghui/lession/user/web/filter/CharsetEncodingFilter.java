package cn.yufenghui.lession.user.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 12:33
 * @since
 */
public class CharsetEncodingFilter implements Filter {

    private String encoding = null;

    private ServletContext servletContext;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpRequest.setCharacterEncoding(encoding);
            httpResponse.setCharacterEncoding(encoding);

            servletContext.log("设置请求、相应编码为: " + encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
