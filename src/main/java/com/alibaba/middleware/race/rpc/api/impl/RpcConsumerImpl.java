package com.alibaba.middleware.race.rpc.api.impl;

import com.alibaba.middleware.race.rpc.aop.ConsumerHook;
import com.alibaba.middleware.race.rpc.api.RpcConsumer;
import com.alibaba.middleware.race.rpc.async.ResponseCallbackListener;
import com.alibaba.middleware.race.rpc.handler.RpcConsumerHandler;
import com.alibaba.middleware.race.rpc.model.RpcRequest;
import com.alibaba.middleware.race.rpc.model.RpcResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcConsumerImpl extends RpcConsumer{

    private Class<?> interfaceClazz;
    private String version;
    private int clientTimeout;


    public RpcConsumer interfaceClass(Class<?> interfaceClass){
        this.interfaceClazz = interfaceClass;
        return this;
    }

    public RpcConsumer version(String version){
        this.version = version;
        return this;
    }

    public RpcConsumer clientTimeout(int clientTimeout){
        this.clientTimeout = clientTimeout;
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
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        //
        RpcConsumerHandler consumerHandler = new RpcConsumerHandler();
        RpcResponse response = consumerHandler.send(request);

        if (response.isError()) {
            throw response.getError();
        }

        return response.getAppResponse();
    }


}
