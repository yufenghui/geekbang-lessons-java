package cn.yufenghui.lession.rest.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static cn.yufenghui.lession.rest.util.URLUtils.resolveVariables;
import static cn.yufenghui.lession.rest.util.URLUtils.toTemplateVariables;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;

/**
 * @author Yu Fenghui
 * @date 2021/3/31 14:33
 * @since
 */
public class URLUtilsTest {

    @Test
    public void testResolveVariables() {
        Map<String, Object> templateValues = new HashMap<>();

        templateValues.put("a", 1);
        templateValues.put("b", 2);
        templateValues.put("c", 3);

        String value = resolveVariables("/{a}/{b}/{c}", templateValues, true);
        assertEquals("/1/2/3", value);

        value = resolveVariables("/{a}/{b}/{c}", emptyMap(), true);
        assertEquals("/{a}/{b}/{c}", value);

        value = resolveVariables("/{a}/{b}/{d}/{c}", templateValues, true);
        assertEquals("/1/2/{d}/3", value);
    }

    @Test
    public void testToTemplateVariables() {

        Map<String, Object> templateVariables = toTemplateVariables(null, null);
        assertEquals(emptyMap(), templateVariables);

        templateVariables = toTemplateVariables("", null);
        assertEquals(emptyMap(), templateVariables);

        templateVariables = toTemplateVariables("     ", null);
        assertEquals(emptyMap(), templateVariables);

        templateVariables = toTemplateVariables("/", null);
        assertEquals(emptyMap(), templateVariables);

        templateVariables = toTemplateVariables("/{a}/{b}/{c}", 1, 2);
        assertEquals(Maps.of("a", 1, "b", 2, "c", null), templateVariables);

        templateVariables = toTemplateVariables("/{a}/{b}/{c}", 1, 2, 3);
        assertEquals(Maps.of("a", 1, "b", 2, "c", 3), templateVariables);

        templateVariables = toTemplateVariables("/{a}/{b}/{b}", 1, 2, 3);
        assertEquals(Maps.of("a", 1, "b", 2), templateVariables);

    }

}
