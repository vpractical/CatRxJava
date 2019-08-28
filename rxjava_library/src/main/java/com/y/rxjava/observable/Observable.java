package com.y.rxjava.observable;

import com.y.rxjava.Function;
import com.y.rxjava.ObservableOnSubscribe;
import com.y.rxjava.ThreadScheduler;
import com.y.rxjava.observer.Observer;

public abstract class Observable<T> {

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source){
        return new ObservableCreate<>(source);
    }

    public void subscribe(Observer<? super T> observer){
        subscribeImpl(observer);
    }

    public <R> Observable<R> map(Function<? super T,? extends R> function){
        return new ObservableMap<>(this,function);
    }


    public Observable<T> subscribeOn(ThreadScheduler scheduler){
        return new ObservableSubscribeOn<>(this,scheduler);
    }

    public Observable<T> observerOn(ThreadScheduler scheduler){
        return new ObservableObserverOn<>(this,scheduler);
    }

    protected abstract void subscribeImpl(Observer<? super T> observer);

}
