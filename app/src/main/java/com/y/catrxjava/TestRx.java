package com.y.catrxjava;

import androidx.annotation.NonNull;

import com.y.rxjava.Disposable;
import com.y.rxjava.Emitter;
import com.y.rxjava.Function;
import com.y.rxjava.ObservableOnSubscribe;
import com.y.rxjava.ThreadScheduler;
import com.y.rxjava.observable.Observable;
import com.y.rxjava.observer.Observer;

import java.util.ArrayList;
import java.util.List;

class TestRx {
    static void test() {
        final List<User> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new User("C" + i, i % 2 == 0 ? "男" : "女", (4 + i) * 4));
        }

        Observable
                .create(new ObservableOnSubscribe<List<User>>() {
                    @Override
                    public void subscribe(Emitter<List<User>> emitter) {
                        emitter.next(list);
                        emitter.complete();
                        log("create : " + Thread.currentThread().getName());
                    }
                })
                .map(new Function<List<User>, List<User>>() {
                    @Override
                    public List<User> apply(List<User> users) {
                        for (User u : users) {
                            u.age = 3;
                        }
                        return users;
                    }
                })
                .subscribeOn(ThreadScheduler.IO)
                .observerOn(ThreadScheduler.MAIN)
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        log("onSubscribe()");
                        log("onSubscribe() : " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(List<User> val) {
                        log("onNext(): " + val.toString());
                        log("onNext() : " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log("onError(): " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete()");
                    }
                });
    }

    static final class User {
        String name, sex;
        int age;

        User(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        @NonNull
        @Override
        public String toString() {
            return "" + name + "-" + sex + "-" + age;
        }
    }

    static void log(Object s) {
        System.out.println(s.toString());
    }
}
