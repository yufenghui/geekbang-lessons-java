package cn.yufenghui.lession.rest.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yu Fenghui
 * @date 2021/3/31 14:33
 * @since
 */
public interface Maps {

    static Map of(Object... values) {
        Map map = new LinkedHashMap();
        int length = values.length;
        for (int i = 0; i < length; ) {
            map.put(values[i++], values[i++]);
        }
        return map;
    }

}
