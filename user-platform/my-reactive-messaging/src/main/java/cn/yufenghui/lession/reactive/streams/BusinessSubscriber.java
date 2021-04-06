package cn.yufenghui.lession.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author Yu Fenghui
 * @date 2021/4/6 17:01
 * @since
 */
public class BusinessSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;

    private int count = 0;

    private final long maxRequest;

    public BusinessSubscriber(long maxRequest) {
        this.maxRequest = maxRequest;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.subscription.request(maxRequest);
    }

    @Override
    public void onNext(T t) {
        if(count++ > maxRequest - 1) {
            subscription.cancel();
            return;
        }

        System.out.println("收到数据: " + t);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("消费数据遇到异常: " + t);
    }

    @Override
    public void onComplete() {
        System.out.println("消费数据完成");
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
