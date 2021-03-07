package cn.yufenghui.lession.user.service;

import cn.yufenghui.lession.user.domain.User;

import java.util.Collection;

/**
 * 用户服务
 *
 * @author Yu Fenghui
 * @date 2021/3/1 13:49
 * @since
 */
public interface UserService {

    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean register(User user);

    /**
     * 注销用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean deregister(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return
     */
    boolean update(User user);

    User queryUserById(Long id);

    User queryUserByNameAndPassword(String name, String password);

    Collection<User> getAll();

}
