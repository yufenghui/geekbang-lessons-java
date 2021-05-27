package cn.yufenghui.lession.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 16:40
 * @since
 */
@Intercepts(
        {
                @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
                @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        }
)
public class TestInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // get execute result
        Object proceedReslut = invocation.proceed();

        System.out.println("[TestInterceptor]: test interceptor");

        return proceedReslut;
    }

}
