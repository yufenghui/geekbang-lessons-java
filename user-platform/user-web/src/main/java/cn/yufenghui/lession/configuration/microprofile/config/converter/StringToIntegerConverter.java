package cn.yufenghui.lession.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author Yu Fenghui
 * @date 2021/3/18 10:04
 * @since
 */
public class StringToIntegerConverter implements Converter<Integer> {

    @Override
    public Integer convert(String value) throws IllegalArgumentException, NullPointerException {
        Integer returnValue = null;
        try {
            returnValue = Integer.valueOf(value);
        } catch (Exception e) {

        }
        return returnValue;
    }

}
