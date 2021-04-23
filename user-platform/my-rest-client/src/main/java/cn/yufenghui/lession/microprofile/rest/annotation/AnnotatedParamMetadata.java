package cn.yufenghui.lession.microprofile.rest.annotation;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;

/**
 * The metadata of Annotated @*Param
 *
 * @author Yu Fenghui
 * @date 2021/4/22 10:13
 * @see PathParam
 * @see QueryParam
 * @see MatrixParam
 * @see FormParam
 * @see CookieParam
 * @see HeaderParam
 * @see DefaultValue
 * @since 1.0.0
 */
public class AnnotatedParamMetadata {

    /**
     * The type of annotation.
     */
    private Class<? extends Annotation> annotationType;

    /**
     * The value of value() attribute method. e.g {@link PathParam#value()}.
     */
    private String paramName;

    /**
     * The value of {@link DefaultValue}, may be <code>null</code>.
     */
    private String defaultValue;

    /**
     * The index of the method parameter.
     */
    private int parameterIndex;


    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public void setParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }
}
