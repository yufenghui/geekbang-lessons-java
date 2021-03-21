package cn.yufenghui.lession.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author : yufenghui
 * @date : 2021/3/21 21:27
 * @Description:
 */
public class StringConverter implements Converter<String> {

    @Override
    public String convert(String value) throws IllegalArgumentException, NullPointerException {
        return value;
    }

}
