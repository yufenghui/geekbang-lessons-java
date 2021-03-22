package cn.yufenghui.lession.configuration.microprofile.config;

import cn.yufenghui.lession.configuration.microprofile.config.converter.Converters;
import cn.yufenghui.lession.configuration.microprofile.config.converter.IntegerConverter;
import cn.yufenghui.lession.configuration.microprofile.config.converter.StringConverter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Yu Fenghui
 * @date 2021/3/22 14:04
 * @since
 */
public class ConvertersTest {

    private Converters converters;

    @BeforeClass
    public static void prepare() {

    }

    @Before
    public void init() {
        converters = new Converters(this.getClass().getClassLoader());
    }

    @Test
    public void testResolveConvertedType() {
        assertEquals(Integer.class, converters.resolveConverterType(new IntegerConverter()));
        assertEquals(String.class, converters.resolveConverterType(new StringConverter()));
    }

}
