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
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestMultiMessageClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap()
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TestMultiMessageClientClientInitializer());
            ChannelFuture channelFuture = bootstrap
                    .connect("127.0.0.1", 8089)
                    .sync();

            Channel channel = bootstrap.connect("127.0.0.1", 8089).sync().channel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, CharsetUtil.UTF_8));
            String line;
            while ((line = reader.readLine()) != null){
                channel.writeAndFlush(createObject(line));
            }

        }finally {
            loopGroup.shutdownGracefully();
        }
    }

    private static Object createObject(String info) {
        if("0".equals(info)){
            MultiDataInfo.Person person = MultiDataInfo.Person.newBuilder().setName("gdw").setAge(20).setAddress("address").build();
            return MultiDataInfo.MyMessage.newBuilder().setPerson(person).setDataType(MultiDataInfo.MyMessage.DataType.PERSON).build();
        }

        if("1".equals(info)){
            MultiDataInfo.Dog dog = MultiDataInfo.Dog.newBuilder().setName("i am dog").setAge(20).build();
            return MultiDataInfo.MyMessage.newBuilder().setDog(dog).setDataType(MultiDataInfo.MyMessage.DataType.DOG).build();
        }

        MultiDataInfo.Cat cat = MultiDataInfo.Cat.newBuilder().setName("i am cat").setCity("bei jin").build();
        return MultiDataInfo.MyMessage.newBuilder().setCat(cat).setDataType(MultiDataInfo.MyMessage.DataType.CAT).build();
    }
}

class TestMultiMessageClientClientInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(MultiDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
    }
}