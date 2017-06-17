package com.gizwits.noti2.client;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feel on 2017/6/16.
 */
public class MessageHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private Message message;

    private Gson gson = new Gson();

    private NotiClient client;

    public static final String PING = "{\"cmd\":\"ping\"}" + System.getProperty("line.separator");


    public MessageHandler(NotiClient client, Message message) {

        this.client = client;
        this.message = message;

    }

    /**
     * 登入认证
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(gson.toJson(message) + System.getProperty("line.separator"));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        JSONObject messageObject = JSONObject.parseObject(msg);
        String cmd = messageObject.getString("cmd");

        switch (Command.getCmd(cmd)) {
            case PONG:
                logger.info("client receive the server heartbeat:{}", msg);
                break;
            case LOGIN_RES:
                boolean isSuceess = messageObject.getJSONObject("data").getBoolean("result");

                if (isSuceess) {

                    logger.info("client login success");

                    while (this.client.isRunning()) {

                        if (!ctx.channel().isOpen() || !ctx.channel().isWritable()) {

                            logger.info("channel isOpen:{},channel isWritable :{}", ctx.channel().isOpen(), ctx.channel().isWritable());

                            client.restart();

                        } else if (ctx.channel().isWritable()) {
                            String message = gson.toJson(client.message()) + System.getProperty("line.separator");
                            ctx.writeAndFlush(message);

                        }
                    }

                } else {
                    logger.error("client  login  error:{}", msg);
                }

                break;
            case EVENT_PUSH:

                logger.debug("receive event push  message:{}", msg);

                Future<Boolean> submit = ctx.executor().submit(() -> client.dispatch(msg));

                if (submit.isSuccess()) {

                    String delivery_id = messageObject.getString("delivery_id");
                    String ackMessage = eventAckMessage(delivery_id);
                    ctx.writeAndFlush(ackMessage + System.getProperty("line.separator"));
                }

                break;
            default:
                break;
        }


    }

    private String eventAckMessage(String delivery_id) {

        Map<String, String> map = new HashMap<>();
        map.put("cmd", "event_ack");
        map.put("delivery_id", delivery_id);

        return gson.toJson(map);
    }


    /**
     * 利用写空闲发送心跳检测消息
     * 定时心跳检查
     * 定时心跳也可使用定时器 new HashedWheelTimer()
     * 或者使用定时任务 ctx.channel().eventLoop().scheduleAtFixedRate(() -> ctx.writeAndFlush(PING), 0, 10, TimeUnit.SECONDS);
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    ctx.writeAndFlush(PING);
                    break;
                default:
                    break;
            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        super.exceptionCaught(ctx, cause);

        logger.error("client  connected  error:", cause);

        ctx.close();
    }
}
