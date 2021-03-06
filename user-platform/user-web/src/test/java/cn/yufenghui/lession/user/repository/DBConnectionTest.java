package cn.yufenghui.lession.user.repository;

import cn.yufenghui.lession.user.domain.User;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yufenghui
 * @date : 2021/3/6 18:23
 * @Description:
 */
public class DBConnectionTest {

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

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    static Map<Class, String> typeMethodMappings = new HashMap<>();

    static {
        typeMethodMappings.put(Long.class, "getLong");
        typeMethodMappings.put(String.class, "getString");
    }


    public static void main(String[] args) throws Exception {

        Connection connection = null;
        Statement statement = null;

        try {
            String jdbcURL = "jdbc:derby:db/user-platform;create=true";
            connection = DriverManager.getConnection(jdbcURL);

            statement = connection.createStatement();

            // 创建 users 表
            System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL)); // false
            // 删除 users 表
            System.out.println(statement.execute(DROP_USERS_TABLE_DDL_SQL)); // false

            // 创建 users 表
            System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL)); // false
            System.out.println(statement.executeUpdate(INSERT_USER_DML_SQL));  // 5

            // 执行查询语句（DML）
            ResultSet resultSet = statement.executeQuery("SELECT id,name,password,email,phoneNumber FROM users");


            BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class, Object.class);
            for (PropertyDescriptor descriptor : userBeanInfo.getPropertyDescriptors()) {
                System.out.println(descriptor.getName() + " : " + descriptor.getPropertyType());
            }


            while (resultSet.next()) {

                User user = new User();

                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println("当前表的名称：" + metaData.getTableName(1));
                System.out.println("当前表的列个数：" + metaData.getColumnCount());
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.println("列名称：" + metaData.getColumnLabel(i) + ", 类型：" + metaData.getColumnClassName(i));
                }

                StringBuilder queryAllUsersSQLBuilder = new StringBuilder("SELECT");
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    queryAllUsersSQLBuilder.append(" ").append(metaData.getColumnLabel(i)).append(",");
                }
                // 移除最后一个 ","
                queryAllUsersSQLBuilder.deleteCharAt(queryAllUsersSQLBuilder.length() - 1);
                queryAllUsersSQLBuilder.append(" FROM ").append(metaData.getTableName(1));

                System.out.println(queryAllUsersSQLBuilder);

                System.out.println("-----------------------------------");

                // ORM 映射
                for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
                    String fieldName = propertyDescriptor.getName();
                    Class fieldType = propertyDescriptor.getPropertyType();

                    String methodName = typeMethodMappings.get(fieldType);
                    // 字段映射
                    String columnLabel = mapColumnLabel(fieldName);

                    Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
                    Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);

                    Method setterMethod = propertyDescriptor.getWriteMethod();
                    setterMethod.invoke(user, resultValue);
                }

                System.out.println(user);
            }

        } finally {
            // 删除 users 表
            statement.execute(DROP_USERS_TABLE_DDL_SQL);

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getCause());
                }
            }
        }

    }

    private static String mapColumnLabel(String fieldName) {
        return fieldName;
    }

}
