package cn.yufenghui.lession.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 9:38
 * @since
 */
public interface RestController extends Controller {

    /**
     * 执行
     *
     * @param request
     * @param response
     * @return 返回JSON数据
     * @throws Throwable
     */
    Object execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;

}
