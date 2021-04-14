package cn.yufenghui.lession.user.web.listener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 14:15
 * @since
 */
public class CacheableHttpSessionActivationListener implements HttpSessionActivationListener {

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println(String.format("sessionWillPassivate: %s", se.getSession().getId()));
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println(String.format("sessionDidActivate: %s", se.getSession().getId()));
    }

}
