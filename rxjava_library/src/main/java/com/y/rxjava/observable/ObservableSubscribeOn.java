package com.y.rxjava.observable;

import com.y.rxjava.TaskScheduler;
import com.y.rxjava.ThreadScheduler;
import com.y.rxjava.observer.Observer;

class ObservableSubscribeOn<T> extends Observable<T>{
    private Observable<T> observable;
    private ThreadScheduler scheduler;

    ObservableSubscribeOn(Observable<T> observable, ThreadScheduler scheduler){
        this.observable = observable;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeImpl(final Observer<? super T> observer) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                BasicObserver<T> subscribeOnObserver = new BasicObserver<>(observer);
                observable.subscribe(subscribeOnObserver);
            }
        };

        TaskScheduler.run(r,scheduler);
    }

}
