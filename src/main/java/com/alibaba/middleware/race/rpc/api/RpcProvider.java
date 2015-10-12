package com.alibaba.middleware.race.rpc.api;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcProvider{
    private void init()
    {
    }

    public RpcProvider serviceInterface(Class<?> serviceInterface)
    {
        return this;
    }

    public RpcProvider version(String version)
    {
        return this;
    }

    public RpcProvider impl(Object serviceInstance)
    {
        return this;
    }

    public RpcProvider timeout(int timeout)
    {
        return this;
    }

    public RpcProvider serializeType(String serializeType)
    {
        return this;
    }

    public void publish(){
    }
}
