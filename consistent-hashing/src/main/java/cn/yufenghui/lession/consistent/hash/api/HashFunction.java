package cn.yufenghui.lession.consistent.hash.api;

/**
 * @author Yu Fenghui
 * @date 2021/5/19 14:49
 * @since
 */
public interface HashFunction {

    long hash(String key);

}
