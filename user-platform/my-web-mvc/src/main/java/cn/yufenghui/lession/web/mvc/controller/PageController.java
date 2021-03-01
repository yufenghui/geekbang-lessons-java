package cn.yufenghui.lession.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 9:38
 * @since
 */
public interface PageController extends Controller {

    /**
     * 执行
     *
     * @param request
     * @param response
     * @return 视图路径
     * @throws Throwable
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;

}
