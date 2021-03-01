package cn.yufenghui.lession.user.repository;

import cn.yufenghui.lession.user.db.DBConnectionManager;
import cn.yufenghui.lession.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 14:00
 * @since
 */
public class DatabaseUserRepository extends BaseRepository implements UserRepository {

    private static Logger logger = Logger.getLogger(DatabaseUserRepository.class.getName());

    public static final String INSERT_USER_DML_SQL =
            "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
                    "(?,?,?,?)";

    public static final String QUERY_ALL_USERS_DML_SQL = "SELECT id,name,password,email,phoneNumber FROM users";

    public DatabaseUserRepository(DBConnectionManager dbConnectionManager) {
        super(dbConnectionManager);
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean deleteById(Long userId) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User getById(Long userId) {
        return null;
    }

    @Override
    public User getByNameAndPassword(String userName, String password) {
        return executeQuery("SELECT id,name,password,email,phoneNumber FROM users WHERE name=? and password=?",
                resultSet -> {
                    User user = new User();
                    while (resultSet.next()) {
                        mapResultSet(resultSet, user, fieldName -> fieldName);
                    }
                    return user;
                },
                COMMON_EXCEPTION_HANDLER, userName, password);
    }

    @Override
    public Collection<User> getAll() {
        return executeQuery("SELECT id,name,password,email,phoneNumber FROM users", resultSet -> {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                mapResultSet(resultSet, user, fieldName -> fieldName);
                userList.add(user);
            }
            return userList;
        }, COMMON_EXCEPTION_HANDLER);
    }


}
