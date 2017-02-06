package com.legendmohe.rxbus;

/**
 * Created by legendmohe on 16/4/15.
 */
public class RxEvent {

    public static EnumEvent createEnumEvent(Enum type, Object data) {
        EnumEvent enumEvent = new EnumEvent();
        enumEvent.type = type;
        enumEvent.data = data;
        return enumEvent;
    }

    public static EnumEvent createEnumEvent(Enum type) {
        EnumEvent enumEvent = new EnumEvent();
        enumEvent.type = type;
        enumEvent.data = null;
        return enumEvent;
    }

    public static class EnumEvent {
        Enum type;
        Object data;
    }
}
