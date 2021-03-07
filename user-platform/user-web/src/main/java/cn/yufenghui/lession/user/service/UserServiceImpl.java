package cn.yufenghui.lession.user.service;

import cn.yufenghui.lession.user.domain.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * @author : yufenghui
 * @date : 2021/3/7 00:13
 * @Description:
 */
public class UserServiceImpl implements UserService {

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        System.out.println("init UserServiceImpl");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy UserServiceImpl");
    }

    @Override
    public boolean register(User user) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(user);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        if (user.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public Collection<User> getAll() {

        Query query = entityManager.createQuery("select u from User u");
        List<User> result = query.getResultList();

        return result;
    }

}
