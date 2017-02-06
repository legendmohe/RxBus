package com.legendmohe.rxbus;

import com.legendmohe.rxbus.annotation.RxSubscribe;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by legendmohe on 16/4/15.
 */
public class RxEventResolver implements RxEventSubscriber.EventResolver {

    @Override
    public void invokeSubscribeMethod(Object host, Method method, Object event) throws InvocationTargetException, IllegalAccessException {
        switch (method.getParameterTypes().length) {
            case 1:
                method.invoke(host, event);
                break;
        }
    }

    @Override
    public Class resolveSubscriptionClassFromSourceEvent(Object rawEvent) {
        return rawEvent.getClass();
    }

    @Override
    public Class<? extends Annotation> resolveAnnotationClass() {
        return RxSubscribe.class;
    }
}
