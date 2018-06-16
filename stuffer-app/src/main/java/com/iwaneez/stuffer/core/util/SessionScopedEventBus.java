package com.iwaneez.stuffer.core.util;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.iwaneez.stuffer.core.StufferUI;

/**
 * EventBus wrapper
 */
public class SessionScopedEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);


    public static void post(final Object event) {
        StufferUI.getSessionScopedEventBus().eventBus.post(event);
    }

    public static void register(final Object object) {
        StufferUI.getSessionScopedEventBus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        StufferUI.getSessionScopedEventBus().eventBus.unregister(object);
    }

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        throwable.printStackTrace();
    }
}
