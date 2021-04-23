package cn.yufenghui.lession.microprofile.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.spi.RestClientBuilderResolver;

/**
 * Default {@link RestClientBuilderResolver} implementation
 *
 * @author Yu Fenghui
 * @date 2021/4/23 14:05
 * @since
 */
public class DefaultRestClientBuilderResolver extends RestClientBuilderResolver {

    @Override
    public RestClientBuilder newBuilder() {
        return new DefaultRestClientBuilder(getClass().getClassLoader());
    }

}
