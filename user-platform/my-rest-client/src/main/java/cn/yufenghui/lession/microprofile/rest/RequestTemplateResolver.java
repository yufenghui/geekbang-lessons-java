package cn.yufenghui.lession.microprofile.rest;

import java.lang.reflect.Method;

/**
 * {@link RequestTemplate} resolver
 *
 * @author Yu Fenghui
 * @date 2021/4/22 11:35
 * @since
 */
public interface RequestTemplateResolver {

    /**
     * Resolve an instance of {@link RequestTemplate} by the specified
     * resource class and method.
     *
     * @param resourceClass  the resource class
     * @param resourceMethod the resource method
     * @return {@link RequestTemplate} if can be resolved
     */
    RequestTemplate resolve(Class<?> resourceClass, Method resourceMethod);

}
