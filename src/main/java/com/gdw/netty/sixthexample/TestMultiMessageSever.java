package com.gdw.netty.sixthexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMultiMessageSever {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new TestMultiMessageInitializer());
            ChannelFuture channelFuture = bootstrap.bind(8089).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class TestMultiMessageInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(MultiDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new TestMultiMessageHandler());
    }
}

class TestMultiMessageHandler extends SimpleChannelInboundHandler<MultiDataInfo.MyMessage>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MultiDataInfo.MyMessage msg) throws Exception {
        switch (msg.getDataType()){
            case PERSON:
                System.out.println(msg.getPerson().getName());
                System.out.println(msg.getPerson().getAddress());
                System.out.println(msg.getPerson().getAge());
                break;
            case DOG:
                System.out.println(msg.getDog().getName());
                System.out.println(msg.getDog().getAge());
                break;
            case CAT:
                System.out.println(msg.getCat().getName());
                System.out.println(msg.getCat().getCity());
        }
    }
}