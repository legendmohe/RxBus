package com.legendmohe.rxbus;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by legendmohe on 16/4/14.
 */
public class RxBus {

    public static RxEventSubscriber.EventResolver DEFAULT_SUBSCRIPTION_RESOLVER = new RxEventResolver();

    public static Scheduler DEFAULT_SCHEDULER = AndroidSchedulers.mainThread();

    private Map<Object, Subscriber<Object>> mEventHandlerMap = new HashMap<>();

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void post(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return _bus.asObservable();
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }

    public void register(Object host) {
        new RegisterItem().register(host);
    }

    public synchronized void register(Object host, RxEventSubscriber.EventResolver resolver, Scheduler scheduler) {
        if (mEventHandlerMap.containsKey(host))
            throw new IllegalStateException("host has been registered");

        RxEventSubscriber handler = new RxEventSubscriber(host, resolver);
        if (scheduler != null) {
            _bus.observeOn(scheduler).subscribe(handler);
        } else {
            _bus.subscribe(handler);
        }
        mEventHandlerMap.put(host, handler);
    }

    public synchronized void unregister(Object host) {
        if (!mEventHandlerMap.containsKey(host))
            throw new IllegalArgumentException("host has not been registered");
        mEventHandlerMap.get(host).unsubscribe();
        mEventHandlerMap.remove(host);
    }

    public RegisterItem withSubscriptionResolver(RxEventSubscriber.EventResolver resolver) {
        return new RegisterItem().withSubscriptionResolver(resolver);
    }

    public RegisterItem withScheduler(Scheduler scheduler) {
        return new RegisterItem().withScheduler(scheduler);
    }

    //////////////////////////////////////////////////////////////////////

    public class RegisterItem {
        RxEventSubscriber.EventResolver resolver = DEFAULT_SUBSCRIPTION_RESOLVER;
        Scheduler scheduler = DEFAULT_SCHEDULER;

        public void register(Object host) {
            // default event resolver
            RxBus.this.register(host, resolver, scheduler);
        }

        public RegisterItem withSubscriptionResolver(RxEventSubscriber.EventResolver resolver) {
            this.resolver = resolver;
            return this;
        }

        public RegisterItem withScheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }
    }
}
