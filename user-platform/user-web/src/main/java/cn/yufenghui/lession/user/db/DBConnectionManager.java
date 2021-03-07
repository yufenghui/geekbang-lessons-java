package cn.yufenghui.lession.user.db;

import cn.yufenghui.lession.context.ComponentContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 13:48
 * @since
 */
public class DBConnectionManager {

    private String dbType = "jndi";
    private String jdbcUrl = "jdbc:derby:db/user-platform;create=true";
    private String jndiName = "jdbc/UserPlatformDB";

    private Connection connection;

    public DBConnectionManager() {
    }

    public DBConnectionManager(String dbType) {
        this.dbType = dbType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Connection getConnection() {
        if ("jdbc".equalsIgnoreCase(this.dbType)) {
            this.connection = getJDBCConnection(jdbcUrl);
        } else if ("jndi".equalsIgnoreCase(this.dbType)) {
            this.connection = getJNDIConnection(jndiName);
        }

        return this.connection;
    }

    private Connection getJDBCConnection(String jdbcURL) {
        try {
            this.connection = DriverManager.getConnection(jdbcURL);

        } catch (SQLException t) {
            System.out.println("创建数据库连接失败。" + t.getMessage());
            t.printStackTrace();
        }
        return this.connection;
    }

    private Connection getJNDIConnection(String jndiName) {
        try {
            DataSource pool = ComponentContext.getInstance().getComponent(jndiName);
            this.connection = pool.getConnection();

        } catch (Exception t) {
            System.out.println("创建数据库连接失败。" + t.getMessage());
            t.printStackTrace();
        }

        return this.connection;
    }

    public void releaseConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

}
