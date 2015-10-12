package com.alibaba.middleware.race.rpc.async;

import com.alibaba.middleware.race.rpc.model.RpcResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResponseFuture implements Future<Object>
{
    public static ThreadLocal<Future<Object>> futureThreadLocal = new ThreadLocal();

    public static Object getResponse(long timeout)
            throws InterruptedException
    {
        if (null == futureThreadLocal.get())
            throw new RuntimeException("Thread [" + Thread.currentThread() + "] have not set the response future!");

        try
        {
            RpcResponse response = (RpcResponse)(RpcResponse)((Future)futureThreadLocal.get()).get(timeout, TimeUnit.MILLISECONDS);
            if (response.isError())
                throw new RuntimeException(response.getErrorMsg());

            return response.getAppResponse();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException("Time out", e);
        }
    }

    public void setFuture(Future<Object> future) {
        futureThreadLocal.set(future);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
