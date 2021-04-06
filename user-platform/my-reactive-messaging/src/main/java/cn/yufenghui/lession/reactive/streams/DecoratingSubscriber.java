package cn.yufenghui.lession.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

/**
 * @author Yu Fenghui
 * @date 2021/4/6 16:39
 * @since
 */
class DecoratingSubscriber<T> implements Subscriber<T> {

    private final Subscriber<T> source;

    private final Logger logger;

    private long maxRequest = -1;

    private boolean canceled = false;

    private boolean completed = false;

    private long requestCount = 0;

    public DecoratingSubscriber(Subscriber<T> source) {
        this.source = source;
        this.logger = Logger.getLogger(source.getClass().getName());
    }

    @Override
    public void onSubscribe(Subscription s) {
        source.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
        assertRequest();

        if(isComplete()) {
            logger.severe("The data subscription is completed, The method should not be invoke again!");
            return;
        }

        if(isCanceled()) {
            logger.warning(String.format("The subscriber has canceled the data subscription, " +
                    "current data[%s] will be ignored!", t));
            return;
        }

        if(requestCount == maxRequest && maxRequest < Long.MAX_VALUE) {
            onComplete();
            logger.warning(String.format("The number of requests is up to the max threshold[%d], " +
                    "the data subscription is completed!", maxRequest));
            return;
        }

        next(t);
    }

    private void next(T t) {
        try {
            source.onNext(t);
        } catch (Throwable tw) {
            onError(tw);
        } finally {
            requestCount++;
        }
    }

    private void assertRequest() {
        if(maxRequest < 1) {
            throw new IllegalStateException("The number of request must be initialized before " +
                    "Subscriber#onNext(Object) method, please set the positive number to " +
                    "Subscriber#request(int) method on Publisher#subscriber(Subscriber) phase.");
        }
    }

    @Override
    public void onError(Throwable t) {
        source.onError(t);
    }

    @Override
    public void onComplete() {
        source.onComplete();
        completed = true;
    }

    public void setMaxRequest(long maxRequest) {
        this.maxRequest = maxRequest;
    }

    public void cancel() {
        canceled = true;
    }

    public boolean isComplete() {
        return completed;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Subscriber<T> getSource() {
        return source;
    }

}
