package com.alibaba.middleware.race.rpc.handler;

import com.alibaba.middleware.race.rpc.model.RpcRequest;
import com.alibaba.middleware.race.rpc.model.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<RpcRequest> {


    private final Class serviceClass;
    private final Object serviceInstance;

    public RpcProviderHandler(Class serviceClass,Object serviceInstance) {
        if(serviceClass == null)
            System.out.println("ServiceClass is null");
        this.serviceClass = serviceClass;
        this.serviceInstance = serviceInstance;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) {
        RpcResponse response = new RpcResponse();
        System.out.println(request.getMethodName());
        System.out.println(request.getClassName());
        try {
            Object result = handle(request);
            response.setAppResponse(result);
        } catch (Throwable t) {
            response.setError(t.getCause());
//            response.setErrorMsg(t.getCause().toString());
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest request) throws Throwable {
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        System.out.println(serviceClass.toString());
        System.out.println(serviceInstance.getClass());
        FastClass serviceFastClass = FastClass.create(serviceClass);
        System.out.println(serviceFastClass.getName());
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        System.out.println(serviceFastMethod.getName());
        return serviceFastMethod.invoke(serviceInstance, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
