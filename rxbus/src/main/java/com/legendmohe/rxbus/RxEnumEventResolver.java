package com.legendmohe.rxbus;

import com.legendmohe.rxbus.annotation.RxSubscribeEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by legendmohe on 16/4/15.
 */
public class RxEnumEventResolver implements RxEventSubscriber.EventResolver {

    @Override
    public void invokeSubscribeMethod(Object host, Method method, Object event) throws InvocationTargetException, IllegalAccessException {
        RxEvent.EnumEvent enumEvent = (RxEvent.EnumEvent) event;
        switch (method.getParameterTypes().length) {
            case 1:
                method.invoke(host, enumEvent.type);
                break;
            case 2:
                method.invoke(host, enumEvent.type, enumEvent.data);
                break;
        }
    }

    @Override
    public Class resolveSubscriptionClassFromSourceEvent(Object rawEvent) {
        if (!rawEvent.getClass().equals(RxEvent.EnumEvent.class))
            return null;
        return ((RxEvent.EnumEvent) rawEvent).type.getClass();
    }

    @Override
    public Class<? extends Annotation> resolveAnnotationClass() {
        return RxSubscribeEnum.class;
    }
}
