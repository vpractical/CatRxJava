package com.y.rxjava.observable;

import com.y.rxjava.Disposable;
import com.y.rxjava.observer.Observer;

class BasicObserver<T> implements Observer<T> {

    Observer<? super T> observer;

    BasicObserver(Observer<? super T> observer){
        this.observer = observer;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        observer.onSubscribe(disposable);
    }

    @Override
    public void onNext(T val) {
        observer.onNext(val);
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
