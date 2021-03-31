package cn.yufenghui.lession.rest.client;

import cn.yufenghui.lession.rest.core.DefaultResponse;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author : yufenghui
 * @date : 2021/3/31 22:28
 * @Description:
 */
public class HttpPostInvocation implements Invocation {

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> entity;

    HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers = headers;
        this.entity = entity;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Invocation property(String s, Object o) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            setRequestHeaders(connection);

            MediaType mediaType = this.entity.getMediaType();
            if(mediaType == MediaType.APPLICATION_JSON_TYPE) {
                connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_TYPE.getType() + "/" + MediaType.APPLICATION_JSON_TYPE.getSubtype());
            }

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();

            if(mediaType == MediaType.APPLICATION_JSON_TYPE) {
                String jsonBody = (String) this.entity.getEntity();
                IOUtils.write(jsonBody, outputStream, Charset.forName("UTF-8"));
            }

            int statusCode = connection.getResponseCode();

            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);

            return response;
        } catch (IOException e) {
            // TODO Error handler
            e.printStackTrace();
        }
        return null;
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T invoke(GenericType<T> genericType) {
        return null;
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> genericType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> invocationCallback) {
        return null;
    }

}
