package com.gdw.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            Channel channel = bootstrap
                    .connect("127.0.0.1", 8089)
                    .sync()
                    .channel();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, CharsetUtil.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] infos = line.split(",");
                StudentOne.Student student = StudentOne.Student
                        .newBuilder()
                        .setName(infos[0])
                        .setAge(Integer.parseInt(infos[1]))
                        .setAddress(infos[2])
                        .build();
                channel.writeAndFlush(student);
            }
        }finally {
            loopGroup.shutdownGracefully();
        }
    }
}

class MyClientInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new MyClientHandler());
    }
}

class MyClientHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}