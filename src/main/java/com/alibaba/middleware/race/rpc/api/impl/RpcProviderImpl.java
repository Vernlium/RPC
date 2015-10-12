package com.alibaba.middleware.race.rpc.api.impl;

import com.alibaba.middleware.race.rpc.api.RpcProvider;
import com.alibaba.middleware.race.rpc.handler.RpcProviderHandler;
import com.alibaba.middleware.race.rpc.model.RpcDecoder;
import com.alibaba.middleware.race.rpc.model.RpcEncoder;
import com.alibaba.middleware.race.rpc.model.RpcRequest;
import com.alibaba.middleware.race.rpc.model.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Title:
 * Create Time: 2015/7/31 ${Time}
 *
 * @version 1.0
 * @author: anan
 */
public class RpcProviderImpl extends RpcProvider{

    private Class<?> serviceInterface;
    private String version;
    private Object serviceInstance;
    private int timeout;
    private String serializeType;

    public RpcProvider serviceInterface(Class<?> serviceInterface)
    {
        System.out.println(serviceInterface.toString());
        this.serviceInterface = serviceInterface;
        return this;
    }

    public RpcProvider version(String version)
    {
        System.out.println(version);
        this.version = version;
        return this;
    }

    public RpcProvider impl(Object serviceInstance)
    {
        System.out.println(serviceInstance.toString());
        this.serviceInstance = serviceInstance;
        return this;
    }

    public RpcProvider timeout(int timeout)
    {
        this.timeout = timeout;
        return this;
    }

    public RpcProvider serializeType(String serializeType)
    {
        return this;
    }

    public void publish(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            System.out.println(serviceInstance.toString());
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcProviderHandler(serviceInterface, serviceInstance));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String host = "127.0.0.1";//System.getenv("SIP");

            ChannelFuture future = bootstrap.bind(host, 8888).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
