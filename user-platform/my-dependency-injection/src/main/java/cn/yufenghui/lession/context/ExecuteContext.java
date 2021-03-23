package cn.yufenghui.lession.context;

import cn.yufenghui.lession.function.ThrowableFunction;

import javax.naming.Context;

/**
 * @author : yufenghui
 * @date : 2021/3/7 00:28
 * @Description:
 */
public class ExecuteContext {

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    public static <R> R executeInContext(Context context, ThrowableFunction<Context, R> function) {
        return executeInContext(context, function, false);
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    public static <R> R executeInContext(Context context, ThrowableFunction<Context, R> function,
                                   boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                System.out.println(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

}
