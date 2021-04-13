package cn.yufenghui.lession.cache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link KeyValueTypePair} Test
 *
 * @author Yu Fenghui
 * @date 2021/4/13 17:47
 * @since
 */
public class KeyValueTypePairTest {

    @Test
    public void testResolve() {
        KeyValueTypePair keyValueTypePair = KeyValueTypePair.resolve(InMemoryCache.class);
        assertEquals(Object.class, keyValueTypePair.getKeyType());
        assertEquals(Object.class, keyValueTypePair.getValueType());
    }

}
