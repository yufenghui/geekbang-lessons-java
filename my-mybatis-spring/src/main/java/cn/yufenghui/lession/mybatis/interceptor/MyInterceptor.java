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
 * @date 2021/5/11 15:35
 * @since
 */
@Intercepts(
        {
                @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
                @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        }
)
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // SQL execute start time
        long startTimeMillis = System.currentTimeMillis();
        // get execute result
        Object proceedReslut = invocation.proceed();
        // SQL execute end time
        long endTimeMillis = System.currentTimeMillis();
        System.out.println(String.format("[MyInterceptor]: sql execute runnung time：%s millisecond", (endTimeMillis - startTimeMillis)));
        return proceedReslut;
    }

}
