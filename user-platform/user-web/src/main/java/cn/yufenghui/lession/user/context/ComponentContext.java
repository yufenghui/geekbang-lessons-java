package cn.yufenghui.lession.user.context;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.util.NoSuchElementException;

/**
 * @author : yufenghui
 * @date : 2021/3/6 00:03
 * @Description:
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    private static ServletContext servletContext;

    private Context context;

    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    public void init(ServletContext servletContext) {
        try {
            this.context = (Context) new InitialContext().lookup("java:comp/env");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        servletContext.setAttribute(CONTEXT_NAME, this);
        ComponentContext.servletContext = servletContext;
    }


    public <C> C getComponent(String name) {
        C component = null;
        if(this.context != null) {
            try {
                component = (C) this.context.lookup(name);
            } catch (NamingException e) {
                throw new NoSuchElementException(name);
            }
        }
        
        return component;
    }

    public void destroy() {
        if(this.context != null) {
            try {
                this.context.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
