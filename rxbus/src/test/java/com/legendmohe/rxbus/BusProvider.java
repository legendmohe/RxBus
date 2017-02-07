package com.legendmohe.rxbus;

/**
 * Created by legendmohe on 16/3/29.
 */
public class BusProvider {

    private static final RxBus RX_BUS = new RxBus();

    private BusProvider() {
    }

    public static RxBus getRxBusInstance() {
        return RX_BUS;
    }
}
