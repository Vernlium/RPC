package com.alibaba.middleware.race.rpc.async;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public abstract interface ResponseCallbackListener{

    public abstract void onResponse(Object paramObject);

    public abstract void onTimeout();

    public abstract void onException(Exception paramException);
}