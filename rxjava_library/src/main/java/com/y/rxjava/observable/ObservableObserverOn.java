package com.y.rxjava.observable;

import com.y.rxjava.Disposable;
import com.y.rxjava.TaskScheduler;
import com.y.rxjava.ThreadScheduler;
import com.y.rxjava.observer.Observer;

public class ObservableObserverOn<T> extends Observable<T> {
    private Observable<T> observable;
    private ThreadScheduler scheduler;

    ObservableObserverOn(Observable<T> observable, ThreadScheduler scheduler){
        this.observable = observable;
        this.scheduler = scheduler;
    }


    @Override
    protected void subscribeImpl(final Observer<? super T> observer) {
        ObserverOnObserver<T> subscribeOnObserver = new ObserverOnObserver<>(observer,scheduler);
        observable.subscribe(subscribeOnObserver);
    }

    static final class ObserverOnObserver<T> implements Observer<T> {

        Observer<? super T> observer;
        ThreadScheduler scheduler;

        ObserverOnObserver(Observer<? super T> observer,ThreadScheduler scheduler){
            this.observer = observer;
            this.scheduler = scheduler;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(final T val) {
            TaskScheduler.run(new Runnable() {
                @Override
                public void run() {
                    observer.onNext(val);
                }
            },scheduler);
        }

        @Override
        public void onError(final Throwable t) {
            TaskScheduler.run(new Runnable() {
                @Override
                public void run() {
                    observer.onError(t);
                }
            },scheduler);
        }

        @Override
        public void onComplete() {
            TaskScheduler.run(new Runnable() {
                @Override
                public void run() {
                    observer.onComplete();
                }
            },scheduler);
        }
    }
}
