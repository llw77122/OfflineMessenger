package com.offlinemessenger.core.messaging;

/**
 * 消息监听器接口
 */
public interface MessageListener {
    /**
     * 当收到消息时调用
     *
     * @param message 接收到的消息
     */
    void onMessageReceived(Message message);
}
