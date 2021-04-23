package cn.yufenghui.lession.microprofile.rest;

import cn.yufenghui.lession.microprofile.rest.annotation.AnnotatedParamMetadata;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableSet;

/**
 * The template of HTTP request is resolved from the RestClient
 *
 * @author Yu Fenghui
 * @date 2021/4/22 10:11
 * @since
 */
public class RequestTemplate {

    /**
     * @see PathParam
     * @see QueryParam
     * @see MatrixParam
     * @see FormParam
     * @see CookieParam
     * @see HeaderParam
     */
    public static Set<Class<? extends Annotation>> SUPPORTED_PARAM_ANNOTATION_TYPES =
            unmodifiableSet(new LinkedHashSet<>(asList(
                    PathParam.class,
                    QueryParam.class,
                    MatrixParam.class,
                    FormParam.class,
                    CookieParam.class,
                    HeaderParam.class
            )));

    /**
     * The value is resolved from {@link HttpMethod @HttpMethod}
     */
    private String method;

    /**
     * The value is resolved from {@link Path @Path}
     */
    private String uriTemplate;

    /**
     * The @*Param maps to {@link AnnotatedParamMetadata}
     *
     * @see PathParam
     * @see QueryParam
     * @see MatrixParam
     * @see FormParam
     * @see CookieParam
     * @see HeaderParam
     */
    private Map<Class<? extends Annotation>, List<AnnotatedParamMetadata>> annotatedParamMetadataMap = new HashMap<>();

    /**
     * The value is resolved from {@link Consumes @Consumes}
     */
    private Set<String> consumes = new LinkedHashSet<>();

    /**
     * The value is resolved from {@link Produces @Produces}
     */
    private Set<String> produces = new LinkedHashSet<>();


    public RequestTemplate method(String method) {
        this.method = method;
        return this;
    }

    public RequestTemplate uriTemplate(String uriTemplate) {
        this.uriTemplate = uriTemplate;
        return this;
    }

    public RequestTemplate annotatedParamMetadata(List<AnnotatedParamMetadata> annotatedParamMetadata) {
        annotatedParamMetadata.forEach(this::annotatedParamMetadata);
        return this;
    }

    public RequestTemplate annotatedParamMetadata(AnnotatedParamMetadata... annotatedParamMetadata) {
        Arrays.stream(annotatedParamMetadata).forEach(this::annotatedParamMetadata);
        return this;
    }

    public RequestTemplate annotatedParamMetadata(AnnotatedParamMetadata annotatedParamMetadata) {
        Class<? extends Annotation> annotationType = annotatedParamMetadata.getAnnotationType();
        List<AnnotatedParamMetadata> metadataList = annotatedParamMetadataMap.computeIfAbsent(annotationType, type -> new LinkedList<>());
        metadataList.add(annotatedParamMetadata);
        return this;
    }

    public RequestTemplate consumes(String... consumes) {
        this.consumes.addAll(asList(consumes));
        return this;
    }

    public RequestTemplate produces(String... produces) {
        this.produces.addAll(asList(produces));
        return this;
    }

    public List<AnnotatedParamMetadata> getAnnotatedParamMetadata(Class<? extends Annotation> annotationType) {
        return annotatedParamMetadataMap.getOrDefault(annotationType, emptyList());
    }


    public String getMethod() {
        return method;
    }

    public String getUriTemplate() {
        return uriTemplate;
    }

    public Set<String> getConsumes() {
        return unmodifiableSet(consumes);
    }

    public Set<String> getProduces() {
        return unmodifiableSet(produces);
    }

}
