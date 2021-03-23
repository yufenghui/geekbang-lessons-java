package cn.yufenghui.lession.function;

/**
 * @author Yu Fenghui
 * @date 2021/3/1 11:50
 * @since
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {

    /**
     * Apply
     *
     * @param t
     * @return
     * @throws Throwable
     */
    R apply(T t) throws Throwable;

    /**
     * @param t
     * @return
     * @throws RuntimeException
     */
    default R execute(T t) throws RuntimeException {
        R result = null;
        try {
            result = apply(t);
        } catch (Throwable e) {
            throw new RuntimeException(e.getCause());
        }

        return result;
    }

    /**
     * @param t
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> R execute(T t, ThrowableFunction<T, R> function) {
        return function.execute(t);
    }

}
