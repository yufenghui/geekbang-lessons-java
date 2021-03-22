package cn.yufenghui.lession.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author : yufenghui
 * @date : 2021/3/20 19:32
 * @Description:
 */
public class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter<T>> {

    private final Converter<T> converter;

    private final int priority;

    public PrioritizedConverter(Converter<T> converter, int priority) {
        this.converter = converter;
        this.priority = priority;
    }

    public Converter<T> getConverter() {
        return converter;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        return this.converter.convert(value);
    }

    @Override
    public int compareTo(PrioritizedConverter<T> o) {
        return Integer.compare(o.getPriority(), this.getPriority());
    }

}
