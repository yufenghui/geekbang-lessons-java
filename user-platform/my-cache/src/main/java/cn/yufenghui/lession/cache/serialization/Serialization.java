package cn.yufenghui.lession.cache.serialization;

/**
 * @author Yu Fenghui
 * @date 2021/4/14 13:47
 * @since
 */
public interface Serialization {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] bytes);

}
