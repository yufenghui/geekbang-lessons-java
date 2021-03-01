package cn.yufenghui.lession.user.repository;

import cn.yufenghui.lession.user.domain.User;

import java.util.Collection;

/**
 * 用户存储仓库
 *
 * @author Yu Fenghui
 * @date 2021/3/1 13:48
 * @since
 */
public interface UserRepository {

    boolean save(User user);

    boolean deleteById(Long userId);

    boolean update(User user);

    User getById(Long userId);

    User getByNameAndPassword(String userName, String password);

    Collection<User> getAll();

}
