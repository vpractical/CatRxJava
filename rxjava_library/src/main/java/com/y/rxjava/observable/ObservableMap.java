package com.y.rxjava.observable;

import com.y.rxjava.Function;
import com.y.rxjava.observer.Observer;

class ObservableMap<T, R> extends Observable<R> {

    private Observable<T> observable;
    private Function<? super T, ? extends R> function;


    ObservableMap(Observable<T> observable, Function<? super T, ? extends R> function) {
        this.observable = observable;
        this.function = function;
    }

    @Override
    protected void subscribeImpl(Observer<? super R> observer) {
        observable.subscribe(new MapObserver<>(observer,function));
    }

    private static final class MapObserver<T,R> extends Basic2Observer<T,R> {
        Function<? super T,? extends R> function;

        MapObserver(Observer<? super R> observer,Function<? super T,? extends R> function){
            super(observer);
            this.function = function;
        }

        @Override
        public void onNext(T val) {
            R r = function.apply(val);
            observer.onNext(r);
        }


    }
}
