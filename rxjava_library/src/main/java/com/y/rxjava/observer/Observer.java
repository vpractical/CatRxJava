package com.y.rxjava.observer;

import com.y.rxjava.Disposable;

public interface Observer<T> {
    void onSubscribe(Disposable disposable);
    void onNext(T val);
    void onError(Throwable t);
    void onComplete();
}
