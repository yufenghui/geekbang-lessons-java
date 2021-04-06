package cn.yufenghui.lession.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author Yu Fenghui
 * @date 2021/4/6 16:38
 * @since
 */
class SubscriptionAdapter implements Subscription {

    private final DecoratingSubscriber<?> subscriber;

    public SubscriptionAdapter(Subscriber<?> subscriber) {
        this.subscriber = new DecoratingSubscriber<>(subscriber);
    }

    @Override
    public void request(long n) {
        if(n < 1) {
            throw new IllegalArgumentException("The number of elements to request must be larger than 0!");
        }

        this.subscriber.setMaxRequest(n);
    }

    @Override
    public void cancel() {
        this.subscriber.cancel();
    }

    public Subscriber<?> getSubscriber() {
        return subscriber;
    }

    public Subscriber<?> getSourceSubscriber() {
        return subscriber.getSource();
    }


}
