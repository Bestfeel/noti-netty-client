package com.gizwits.noti2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.epoll.Native;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.spi.SelectorProvider;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by feel on 2017/6/16.
 */
public abstract class BaseClient implements IService {

    private static final Logger log = LoggerFactory.getLogger(BaseClient.class);

    private EventLoopGroup workerGroup;
    protected AtomicBoolean isRuning = new AtomicBoolean(false);

    protected int port;
    protected String host;

    private ChannelFuture channelFuture;
    private Bootstrap bootstrap;

    public BaseClient() {

        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

        isRuning.set(false);

        if (this.workerGroup != null) {
            this.workerGroup.shutdownGracefully();
            log.info("noti cliet  stop");
        }

        System.exit(1);
    }

    @Override
    public void doStart() {

        if (useNettyEpoll()) {
            createEpollClient();
        } else {
            createNioClient();
        }
    }


    private boolean boolNettyEpoll() {

        String name = System.getProperty("os.name").toLowerCase(Locale.UK).trim();
        return name.startsWith("linux");//只在linux下使用netty提供的epoll库
    }

    public boolean useNettyEpoll() {

        if (boolNettyEpoll()) {
            try {
                Native.offsetofEpollData();
                return true;
            } catch (UnsatisfiedLinkError error) {
                log.warn("can not load netty epoll, switch nio model.");
            }
        }
        return false;
    }

    @Override
    public void doStop() {

        destory();

    }

    @Override
    public void restart() {


        if (isRunning() && channelFuture.channel().isOpen()) {

            GenericFutureListener eventLister = getEventLister();
            channelFuture.channel().close();

            channelFuture = bootstrap.connect(host, port).addListener(eventLister);
            channelFuture.channel().closeFuture().addListener(eventLister);

        } else {

            destory();
        }

        log.info("client already  restart ");

    }

    @Override
    public boolean isRunning() {
        return isRuning.get();
    }

    private ChannelFactory<? extends Channel> getChannelFactory() {

        if (useNettyEpoll()) {
            return EpollSocketChannel::new;
        } else {
            return NioSocketChannel::new;
        }

    }


    private void createNioClient() {
        if (this.workerGroup == null) {

            NioEventLoopGroup worker = new NioEventLoopGroup(1, new DefaultThreadFactory("--client--"), SelectorProvider.provider());
            worker.setIoRatio(getIoRate());
            this.workerGroup = worker;
        }

        createClient();

    }

    private void createEpollClient() {
        if (this.workerGroup == null) {
            EpollEventLoopGroup worker = new EpollEventLoopGroup(1, new DefaultThreadFactory("--client--"));
            worker.setIoRatio(getIoRate());

            this.workerGroup = worker;
        }

        createClient();
    }


    private void createClient() {

        GenericFutureListener eventLister = getEventLister();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channelFactory(getChannelFactory());
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.TCP_NODELAY, true)//TCP立即发包
                    .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 128 * 1024)) //设置高低水位的方式进行限流
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(getChannelHandler());

            channelFuture = bootstrap.connect(host, port).addListener(eventLister).sync();

            channelFuture.channel().closeFuture().addListener(eventLister);

            isRuning.set(true);

        } catch (Exception e) {

            log.error("Client start exception", e);
            throw new RuntimeException("Client start exception, port=" + port, e);

        }
    }

    private int getIoRate() {
        return 70;
    }


    public abstract ChannelHandler getChannelHandler();


    public abstract GenericFutureListener getEventLister();


}