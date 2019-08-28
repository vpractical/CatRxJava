package com.y.rxjava;

public interface Emitter<T> {

    void next(T val);
    void error(Throwable t);
    void complete();

}
