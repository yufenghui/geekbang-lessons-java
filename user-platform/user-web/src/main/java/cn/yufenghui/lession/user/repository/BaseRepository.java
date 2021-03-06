package cn.yufenghui.lession.user.repository;

import cn.yufenghui.lession.function.ThrowableFunction;
import cn.yufenghui.lession.user.context.ComponentContext;
import cn.yufenghui.lession.user.db.DBConnectionManager;
import org.apache.commons.lang.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 14:31
 * @since
 */
public abstract class BaseRepository {

    private static Logger logger = Logger.getLogger(BaseRepository.class.getName());

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    protected static Map<Class, String> resultSetMethodMappings = new HashMap<>();

    protected static Map<Class, String> preparedStatementMethodMappings = new HashMap<>();

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    protected static Map<Class, String> typeMethodMappings = new HashMap<>();

    static {
        resultSetMethodMappings.put(Long.class, "getLong");
        resultSetMethodMappings.put(String.class, "getString");

        preparedStatementMethodMappings.put(Long.class, "setLong"); // long
        preparedStatementMethodMappings.put(String.class, "setString"); //

        typeMethodMappings.put(Long.class, "getLong");
        typeMethodMappings.put(String.class, "getString");
    }

    /**
     * 通用处理方式
     */
    protected static final Consumer<Throwable> COMMON_EXCEPTION_HANDLER = e -> logger.log(Level.SEVERE, e.getMessage());

    private final DBConnectionManager dbConnectionManager;

    public BaseRepository() {
        this.dbConnectionManager = ComponentContext.getInstance().getComponent("bean/DBConnectionManager");
    }

    protected Connection getConnection() {
        return dbConnectionManager.getConnection();
    }


    protected <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> throwableFunction,
                                 Consumer<Throwable> exceptionHandler, Object... args) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];

                Class argType = arg.getClass();
                Class primitiveType = ClassUtils.wrapperToPrimitive(argType);

                if (primitiveType == null) {
                    primitiveType = argType;
                }

                String methodName = preparedStatementMethodMappings.get(argType);
                Method method = PreparedStatement.class.getMethod(methodName, int.class, primitiveType);
                method.invoke(preparedStatement, i + 1, arg);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            return throwableFunction.apply(resultSet);
        } catch (Throwable t) {
            exceptionHandler.accept(t);
        }

        return null;
    }


    protected <T> void mapResultSet(ResultSet resultSet, T t, ThrowableFunction<String, String> fieldNameMappingFunction) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(t.getClass(), Object.class);
        } catch (Exception e) {
            System.out.println("获取BeanInfo失败。");
            return;
        }

        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            try {
                String fieldName = propertyDescriptor.getName();
                Class fieldType = propertyDescriptor.getPropertyType();

                String methodName = typeMethodMappings.get(fieldType);
                // 字段映射
                String columnLabel = fieldNameMappingFunction.apply(fieldName);

                Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
                Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);

                Method setterMethod = propertyDescriptor.getWriteMethod();
                setterMethod.invoke(t, resultValue);
            } catch (Throwable throwable) {
                System.out.println("属性转换失败: " + propertyDescriptor.getName());
            }
        }

    }

}
