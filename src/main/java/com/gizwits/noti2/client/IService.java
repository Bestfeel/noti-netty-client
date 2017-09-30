package com.gizwits.noti2.client;
/**
 * Created by feel on 2017/6/16.
 */
public interface IService {

    void init();

    void doStart();

    void doStop();

    void restart();

    boolean isRunning();

    void destory();

}
