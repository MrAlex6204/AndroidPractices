package com.mralex6204.android.androidchat.lib;

/**
 * Created by oavera on 4/07/16.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
