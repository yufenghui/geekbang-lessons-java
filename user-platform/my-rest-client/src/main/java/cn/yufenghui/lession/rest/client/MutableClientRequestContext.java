package cn.yufenghui.lession.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.*;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;

/**
 * @author Yu Fenghui
 * @date 2021/3/31 14:06
 * @since
 */
public class MutableClientRequestContext implements ClientRequestContext {

    private URI uri;

    private String method;

    private MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

    private Map<String, Object> properties = new HashMap<>();

    private final Date date;

    public MutableClientRequestContext() {
        this.date = new Date();
    }

    @Override
    public Object getProperty(String name) {
        return properties.get(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public void setProperty(String name, Object object) {
        properties.put(name, object);
    }

    @Override
    public void removeProperty(String name) {
        properties.remove(name);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public MultivaluedMap<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        return null;
    }

    @Override
    public String getHeaderString(String name) {
        Object headerValue = headers.getFirst(name);
        return headerValue == null ? null : headerValue.toString();
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Locale getLanguage() {
        return null;
    }

    @Override
    public MediaType getMediaType() {
        return null;
    }

    @Override
    public List<MediaType> getAcceptableMediaTypes() {
        return null;
    }

    @Override
    public List<Locale> getAcceptableLanguages() {
        return null;
    }

    @Override
    public Map<String, Cookie> getCookies() {
        return null;
    }

    @Override
    public boolean hasEntity() {
        return false;
    }

    @Override
    public Object getEntity() {
        return null;
    }

    @Override
    public Class<?> getEntityClass() {
        return null;
    }

    @Override
    public Type getEntityType() {
        return null;
    }

    @Override
    public void setEntity(Object entity) {

    }

    @Override
    public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {

    }

    @Override
    public Annotation[] getEntityAnnotations() {
        return new Annotation[0];
    }

    @Override
    public OutputStream getEntityStream() {
        return null;
    }

    @Override
    public void setEntityStream(OutputStream outputStream) {

    }

    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public void abortWith(Response response) {

    }

}
