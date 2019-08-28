package com.y.rxjava.observable;

import com.y.rxjava.Disposable;
import com.y.rxjava.observer.Observer;

abstract class Basic2Observer<T,R> implements Observer<T> {

    Observer<? super R> observer;

    Basic2Observer(Observer<? super R> observer){
        this.observer = observer;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        observer.onSubscribe(disposable);
    }

    @Override
    public void onError(Throwable t) {
        observer.onError(t);
    }

    @Override
    public void onComplete() {
        observer.onComplete();
    }
}
