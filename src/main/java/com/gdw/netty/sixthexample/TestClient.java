package com.gdw.netty.sixthexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.IOException;

public class TestClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TestClientInitializer());
            ChannelFuture channelFuture = bootstrap
                    .connect("127.0.0.1", 8089)
                    .sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}

class TestClientInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(MyDataInfo.Student.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new TestClientHandler());
    }
}

class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Student>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Student msg) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyDataInfo.Student student = MyDataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("北京")
                .build();
        ctx.channel().writeAndFlush(student);
    }
}