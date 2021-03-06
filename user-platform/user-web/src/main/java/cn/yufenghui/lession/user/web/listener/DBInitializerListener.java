package cn.yufenghui.lession.user.web.listener;

import cn.yufenghui.lession.user.context.ComponentContext;
import cn.yufenghui.lession.user.db.DBConnectionManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 13:55
 * @since
 */
public class DBInitializerListener implements ServletContextListener {

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
            "('A','******','a@gmail.com','1') , " +
            "('B','******','b@gmail.com','2') , " +
            "('C','******','c@gmail.com','3') , " +
            "('D','******','d@gmail.com','4') , " +
            "('E','******','e@gmail.com','5')";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DBConnectionManager dbConnectionManager =
                    ComponentContext.getInstance().getComponent("bean/DBConnectionManager");
            Connection connection = dbConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            try {
                statement.execute(DROP_USERS_TABLE_DDL_SQL);
            } catch (Exception e) {

            }
            statement.execute(CREATE_USERS_TABLE_DDL_SQL);
            statement.executeUpdate(INSERT_USER_DML_SQL);

            System.out.println("初始化数据库成功。");
        } catch (Exception e) {
            System.out.println("初始化数据库表失败");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DBConnectionManager dbConnectionManager =
                    ComponentContext.getInstance().getComponent("bean/DBConnectionManager");
            Connection connection = dbConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            try {
                statement.execute(DROP_USERS_TABLE_DDL_SQL);
            } catch (Exception e) {

            }

            System.out.println("清理数据库成功。");
        } catch (Exception e) {
            System.out.println("清理数据库表失败");
            e.printStackTrace();
        }
    }

}
