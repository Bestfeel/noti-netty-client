package com.gizwits.noti2.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by feel on 2017/6/16.
 */
public class BoostrapClient extends BaseClient implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BoostrapClient.class);

    private NotiClient client;

    private Message message;

    public BoostrapClient(NotiClient client) {
        this.client = client;
    }

    @Override
    public void init() {
        if (!isRuning.get()) {
            log.info(" NotiClient is already  init");
        }
    }

    @Override
    public void run() {
        doStart();
    }

    public BoostrapClient setOption(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    public BoostrapClient login(Message message) {

        this.message = message;
        return this;
    }

    public ChannelFuture getChannelFuture() {

        return this.channelFuture;
    }

    private X509TrustManager trustManager() {
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return tm;
    }

    @Override
    public ChannelHandler getChannelHandler() {

        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustManager()}, new java.security.SecureRandom());
                SSLEngine sslEngine = sslContext.createSSLEngine();
                sslEngine.setUseClientMode(true);
                ch.pipeline().addLast(new SslHandler(sslEngine));
                ch.pipeline().addLast(new IdleStateHandler(1, 1, 0, TimeUnit.MINUTES));// 心跳检查
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(new MessageHandler(client, message));

            }
        };
    }


    @Override
    public GenericFutureListener getEventLister() {


        return (ChannelFutureListener) f -> {

            if (f.isSuccess()) {

                if (!f.channel().isOpen() && !f.channel().isWritable()) {

                    log.info("receive client  listen  Exception:channel isOpen:{}, channel isWritable :{}", f.channel().isOpen(), f.channel().isWritable());
                    restart();

                } else {

                    isRuning.set(true);

                    log.info("Started NotiClient port: {}", port);
                }

            } else {

                log.error("Started NotiClient Failed  port:{}", port);

                f.channel().eventLoop().schedule(() -> restart(), 1, TimeUnit.SECONDS);
            }
        };
    }


}