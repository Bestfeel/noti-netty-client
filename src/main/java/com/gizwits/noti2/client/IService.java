package com.gizwits.noti2.client;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public interface IService {

    void init();

    void doStart();

    void doStop();

    void restart();

    boolean isRunning();

    void destory();

}
