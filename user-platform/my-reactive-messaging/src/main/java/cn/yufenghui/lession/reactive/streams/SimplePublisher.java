package cn.yufenghui.lession.reactive.streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yu Fenghui
 * @date 2021/4/6 16:34
 * @since
 */
public class SimplePublisher<T> implements Publisher<T> {

    private List<Subscriber> subscribers = new LinkedList<>();

    @Override
    public void subscribe(Subscriber<? super T> s) {
        SubscriptionAdapter subscription = new SubscriptionAdapter(s);
        s.onSubscribe(subscription);
        subscribers.add(subscription.getSubscriber());
    }

    public void publish(T data) {

        for(Subscriber subscriber : subscribers) {

            DecoratingSubscriber decoratingSubscriber = (DecoratingSubscriber) subscriber;

            if(decoratingSubscriber.isCanceled() || decoratingSubscriber.isComplete()) {
                System.out.println("数据订阅被取消");
                continue;
            }

            subscriber.onNext(data);

        }

    }

    public static void main(String[] args) {
        SimplePublisher publisher = new SimplePublisher();

        publisher.subscribe(new BusinessSubscriber(2));

        for (int i = 1; i <= 5; i++) {
            publisher.publish(i);
            System.out.println("发送数据: " + i);
        }
    }

}
