package com.gizwits.noti2.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public class MessageHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private Message message;

    private Gson gson = new Gson();

    private NotiClient client;

    private static final String lineSeparator = "\n";

    public static final String PING = "{\"cmd\":\"ping\"}" + lineSeparator;


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
        ctx.writeAndFlush(gson.toJson(message) + lineSeparator);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.warn("channel is inactive.");
        client.restart();
        client.startPushMessage();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        if (msg != "" && msg != null) {

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
                        client.startPushMessage();

                    } else {
                        logger.error("client  login  error:{}", msg);
                        ctx.close();
                    }
                    break;
                case EVENT_PUSH:
                    logger.debug("receive event push  message:{}", msg);
                    dispathcMessage(ctx, messageObject);
                    break;
                case REMOTE_CONTROL_RES:

                    JSONArray jsonArray = messageObject.getJSONObject("result").getJSONArray("failed");
                    if (!jsonArray.isEmpty()) {
                        logger.error("remote_control_res  error:{}", msg);
                    }
                    break;
                default:
                    logger.info("{}", msg);
                    break;
            }

        }
    }


    private void dispathcMessage(ChannelHandlerContext ctx, JSONObject messageObject) {

        if (client.isRunning()) {

            Future<Boolean> submit = ctx.executor().submit(() -> client.dispatch(messageObject.toJSONString()));

            submit.addListener((FutureListener) future -> {
                if (submit.isSuccess()) {
                    Object delivery_id = messageObject.get("delivery_id");
                    String ackMessage = eventAckMessage(delivery_id);
                    ctx.writeAndFlush(ackMessage + lineSeparator);
                }
            });
        }
    }

    private String eventAckMessage(Object delivery_id) {

        Map map = new HashMap<>();
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
