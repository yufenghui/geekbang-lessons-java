package cn.yufenghui.lession.factory;

import cn.yufenghui.lession.context.ComponentContext;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author : yufenghui
 * @date : 2021/3/7 00:06
 * @Description:
 */
public class MyEntityManagerFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("emf", getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private DataSource getDataSource() {
        DataSource dataSource = ComponentContext.getInstance().getComponent("jdbc/UserPlatformDB");
        return dataSource;
    }

}
