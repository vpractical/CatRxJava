package com.y.rxjava.observable;

import com.y.rxjava.Disposable;
import com.y.rxjava.Emitter;
import com.y.rxjava.ObservableOnSubscribe;
import com.y.rxjava.observer.Observer;

/**
 * create操作符对应的被观察者
 */
class ObservableCreate<T> extends Observable<T> {
    private ObservableOnSubscribe<T> source;

    ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }


    @Override
    protected void subscribeImpl(Observer<? super T> observer) {
        EmitterCreate<T> emitter = new EmitterCreate<>(observer);
        observer.onSubscribe(emitter);

        try {
            source.subscribe(emitter);
        }catch (Exception e){
            e.printStackTrace();
            observer.onError(e);
        }

    }

    static final class EmitterCreate<T> implements Emitter<T>, Disposable {
        private Observer<? super T> observer;
        private boolean isDisposable;
        EmitterCreate(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void disposable() {
            isDisposable = true;
        }

        @Override
        public boolean isDisposable() {
            return isDisposable;
        }

        @Override
        public void next(T val) {
            if(isDisposable) return;
            observer.onNext(val);
        }

        @Override
        public void error(Throwable t) {
            if(isDisposable) return;
            observer.onError(t);
        }

        @Override
        public void complete() {
            if(isDisposable) return;
            observer.onComplete();
        }
    }
}
