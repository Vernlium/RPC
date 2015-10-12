package com.alibaba.middleware.race.rpc.aop;

import com.alibaba.middleware.race.rpc.model.RpcRequest;


/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public abstract interface ProviderHook
{
    public abstract void before(RpcRequest paramRpcRequest);

    public abstract void after(RpcRequest paramRpcRequest);
}