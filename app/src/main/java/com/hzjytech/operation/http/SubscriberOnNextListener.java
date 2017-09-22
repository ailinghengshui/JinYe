package com.hzjytech.operation.http;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
