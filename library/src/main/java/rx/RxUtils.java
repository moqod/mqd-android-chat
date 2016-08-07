package rx;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 17/03/16
 * Time: 20:26
 */
public class RxUtils {

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> observeUntil(Func0<Boolean> predicate) {
        return observable -> observable.lift(new ConditionalOperator<>(predicate));
    }

    private static final HashMap<Object, CompositeSubscription> sSubscriptions = new HashMap<>();

    public static void manage(Object tag, Subscription subscription) {
        CompositeSubscription subscriptions = sSubscriptions.get(tag);
        if (subscriptions == null) {
            subscriptions = new CompositeSubscription();
            sSubscriptions.put(tag, subscriptions);
        }

        subscriptions.add(subscription);
    }

    public static void unsubscribe(Object tag) {
        CompositeSubscription subscriptions = sSubscriptions.get(tag);
        if (subscriptions != null) {
            subscriptions.unsubscribe();
            sSubscriptions.remove(tag);
        }
    }

    private static class ConditionalOperator<T> implements Observable.Operator<T, T> {

        private Func0<Boolean> mPredicate;

        private ConditionalOperator(Func0<Boolean> predicate) {
            mPredicate = predicate;
        }

        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            return new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    if (notifyEvents()) {
                        subscriber.onCompleted();
                    } else {
                        stop();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (notifyEvents()) {
                        subscriber.onError(e);
                    } else {
                        stop();
                    }
                }

                @Override
                public void onNext(T t) {
                    if (notifyEvents()) {
                        subscriber.onNext(t);
                    } else {
                        stop();
                    }
                }

                private void stop() {
                    unsubscribe();
                }
            };
        }

        private boolean notifyEvents() {
            return mPredicate.call();
        }
    }

    public static <T> Boolean alwaysTrue(T t) {
        return Boolean.TRUE;
    }

}
