package cn.yufenghui.lession.context;

import cn.yufenghui.lession.function.ThrowableAction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author : yufenghui
 * @date : 2021/3/6 00:03
 * @Description:
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static ServletContext servletContext;

    private Context envContext;

    private ClassLoader classLoader;

    private Map<String, Object> componentsMap = new LinkedHashMap<>();


    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }

    public void init(ServletContext servletContext) {
        ComponentContext.servletContext = servletContext;
        servletContext.setAttribute(CONTEXT_NAME, this);

        this.classLoader = servletContext.getClassLoader();

        initEnvContext();
        instantiateComponents();
        initializeComponents();
    }

    private void initEnvContext() {
        if (this.envContext != null) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            close(context);
        }
    }

    private void instantiateComponents() {
        // 遍历获取所有的组件名称
        List<String> componentNames = listAllComponentNames();
        // 通过依赖查找，实例化对象（ Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
        componentNames.forEach(name -> componentsMap.put(name, lookupComponent(name)));

        System.out.println("系统中注册的组件列表: " + componentsMap.keySet());
    }

    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link PostConstruct}</li>
     *  <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    private void initializeComponents() {
        componentsMap.forEach((name, component) -> {

            injectComponents(component);

            processPostConstruct(component);

            processDestroy(component);

        });
    }

    private void injectComponents(Object component) {

        Arrays.stream(component.getClass().getDeclaredFields())
                .filter(field -> {
                    int mods = field.getModifiers();
                    return !Modifier.isStatic(mods) && field.isAnnotationPresent(Resource.class);
                }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String resourceName = resource.name();
            Object injectComponent = this.getComponent(resourceName);

            field.setAccessible(true);
            try {
                field.set(component, injectComponent);
            } catch (IllegalAccessException e) {
                System.out.println("属性注入失败, " + field.getName());
                e.printStackTrace();
            }
        });

    }

    private void processPostConstruct(Object component) {

        Arrays.stream(component.getClass().getMethods())
                .filter(method -> {
                    int parameterCount = method.getParameterCount();
                    return !Modifier.isStatic(method.getModifiers())
                            && parameterCount == 0
                            && method.isAnnotationPresent(PostConstruct.class);
                }).forEach(method -> {

            try {
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void processDestroy(Object component) {

        // TODO: ShutdownHook  此处之前的实现为直接调用，天真了。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            Arrays.stream(component.getClass().getMethods())
                    .filter(method -> {
                        int parameterCount = method.getParameterCount();
                        return !Modifier.isStatic(method.getModifiers())
                                && parameterCount == 0
                                && method.isAnnotationPresent(PreDestroy.class);
                    }).forEach(method -> {

                try {
                    method.invoke(component);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });

        }));

    }


    public <C> C getComponent(String name) {
        return (C) componentsMap.get(name);
    }


    public <C> C lookupComponent(String name) {
        return ExecuteContext.executeInContext(this.envContext, context -> (C) context.lookup(name));
    }

    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    protected List<String> listComponentNames(String name) {
        return ExecuteContext.executeInContext(this.envContext, context -> {
            NamingEnumeration<NameClassPair> e = ExecuteContext.executeInContext(context, ctx -> ctx.list(name), true);

            // 目录 - Context
            // 节点 -
            if (e == null) { // 当前 JNDI 名称下没有子节点
                return Collections.emptyList();
            }

            List<String> fullNames = new LinkedList<>();
            while (e.hasMoreElements()) {
                NameClassPair element = e.nextElement();
                String className = element.getClassName();
                Class<?> targetClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)) {
                    // 如果当前名称是目录（Context 实现类）的话，递归查找
                    fullNames.addAll(listComponentNames(element.getName()));
                } else {
                    // 否则，当前名称绑定目标类型的话话，添加该名称到集合中
                    String fullName = name.startsWith("/") ?
                            element.getName() : name + "/" + element.getName();
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });
    }

    public void destroy() {
        if(this.envContext != null) {
            try {
                this.envContext.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void close(Context context) {
        if (context != null) {
            ThrowableAction.execute(context::close);
        }
    }

}
