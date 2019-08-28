package com.y.rxjava;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskScheduler {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static final ExecutorService SERVICE = Executors.newCachedThreadPool();

    public static void run(Runnable r,ThreadScheduler scheduler){
        boolean isMainThread = Looper.myLooper() == Looper.getMainLooper();
        if(scheduler == ThreadScheduler.DEFAULT){
            r.run();
        }else if(scheduler == ThreadScheduler.MAIN){
            if(isMainThread){
                r.run();
            }else{
                HANDLER.post(r);
            }
        }else{
            SERVICE.submit(r);
        }
    }
}
