package com.gitzhu.common.http.adapter;


import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by qiuxu.zhu on 15-12-14.
 */
public class ListenableFutureAdapter<T> implements ListenableFuture<T> {

    private final com.ning.http.client.ListenableFuture<T> future;

    public ListenableFutureAdapter(com.ning.http.client.ListenableFuture<T> future){
        this.future = future;
    }

    public void addListener(Runnable listener, Executor executor) {
        future.addListener(listener, executor);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return future.isCancelled();
    }

    public boolean isDone() {
        return future.isDone();
    }

    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }
}
