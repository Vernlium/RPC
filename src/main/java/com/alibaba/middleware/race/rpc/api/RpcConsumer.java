package com.alibaba.middleware.race.rpc.api;

import com.alibaba.middleware.race.rpc.aop.ConsumerHook;
import com.alibaba.middleware.race.rpc.async.ResponseCallbackListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcConsumer implements InvocationHandler{

    private Class<?> interfaceClazz;


    private void init(){

    }

    public RpcConsumer interfaceClass(Class<?> interfaceClass){
        this.interfaceClazz = interfaceClass;
        return this;
    }

    public RpcConsumer version(String version){
        return this;
    }

    public RpcConsumer clientTimeout(int clientTimeout){
        return this;
    }

    public RpcConsumer hook(ConsumerHook hook){
        return this;
    }

    public Object instance(){
        return Proxy.newProxyInstance(super.getClass().getClassLoader(), new Class[]{this.interfaceClazz}, this);
    }

    public void asynCall(String methodName){
        asynCall(methodName, null);
    }

    public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener){
    }

    public void cancelAsyn(String methodName){
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
