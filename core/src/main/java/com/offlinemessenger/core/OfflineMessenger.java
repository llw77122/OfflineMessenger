package com.offlinemessenger.core;

import com.offlinemessenger.core.messaging.Message;
import com.offlinemessenger.core.messaging.MessageListener;
import com.offlinemessenger.core.network.Node;
import com.offlinemessenger.core.network.NetworkManager;
import com.offlinemessenger.core.bluetooth.BluetoothManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 离线消息应用主类
 * 提供统一的消息收发接口
 */
public class OfflineMessenger {
    private static final Logger logger = LoggerFactory.getLogger(OfflineMessenger.class);

    private String localNodeId;
    private NetworkManager networkManager;
    private BluetoothManager bluetoothManager;
    private List<MessageListener> messageListeners;
    private boolean initialized = false;

    public OfflineMessenger() {
        this.localNodeId = UUID.randomUUID().toString();
        this.messageListeners = new CopyOnWriteArrayList<>();
        logger.info("OfflineMessenger created with nodeId: {}", localNodeId);
    }

    /**
     * 初始化消息应用
     */
    public void initialize() {
        if (initialized) {
            logger.warn("OfflineMessenger already initialized");
            return;
        }

        logger.info("Initializing OfflineMessenger");
        this.networkManager = new NetworkManager(localNodeId);
        this.bluetoothManager = new BluetoothManager(localNodeId);
        this.initialized = true;
        logger.info("OfflineMessenger initialized successfully");
    }

    /**
     * 启动蓝牙网状网络
     */
    public void startBluetoothMesh() {
        if (!initialized) {
            throw new IllegalStateException("OfflineMessenger not initialized");
        }
        logger.info("Starting Bluetooth mesh network");
        bluetoothManager.startScanning();
        bluetoothManager.setMessageListener(this::handleReceivedMessage);
    }

    /**
     * 停止蓝牙网状网络
     */
    public void stopBluetoothMesh() {
        if (bluetoothManager != null) {
            logger.info("Stopping Bluetooth mesh network");
            bluetoothManager.stopScanning();
        }
    }

    /**
     * 发送点对点消息
     *
     * @param recipientId 接收者ID
     * @param content     消息内容
     * @param type        消息类型
     */
    public void sendMessage(String recipientId, String content, MessageType type) {
        if (!initialized) {
            throw new IllegalStateException("OfflineMessenger not initialized");
        }

        Message message = new Message(
                UUID.randomUUID().toString(),
                localNodeId,
                recipientId,
                content,
                type,
                System.currentTimeMillis()
        );

        logger.info("Sending message to {}: {}", recipientId, content);
        bluetoothManager.sendMessage(message);
    }

    /**
     * 创建群组
     *
     * @param groupName 群组名称
     * @param memberIds 成员ID列表
     * @return 群组ID
     */
    public String createGroup(String groupName, List<String> memberIds) {
        String groupId = UUID.randomUUID().toString();
        logger.info("Creating group: {} with {} members", groupName, memberIds.size());
        // TODO: 实现群组管理
        return groupId;
    }

    /**
     * 发送群组消息
     *
     * @param groupId 群组ID
     * @param content 消息内容
     */
    public void sendGroupMessage(String groupId, String content) {
        if (!initialized) {
            throw new IllegalStateException("OfflineMessenger not initialized");
        }
        logger.info("Sending group message to group {}: {}", groupId, content);
        // TODO: 实现群组消息发送
    }

    /**
     * 注册消息监听器
     *
     * @param listener 消息监听器
     */
    public void onMessageReceived(MessageListener listener) {
        messageListeners.add(listener);
        logger.debug("Message listener registered");
    }

    /**
     * 移除消息监听器
     *
     * @param listener 消息监听器
     */
    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
        logger.debug("Message listener removed");
    }

    /**
     * 处理接收到的消息
     */
    private void handleReceivedMessage(Message message) {
        logger.info("Message received from {}: {}", message.getSender(), message.getContent());
        // 通知所有监听器
        for (MessageListener listener : messageListeners) {
            try {
                listener.onMessageReceived(message);
            } catch (Exception e) {
                logger.error("Error in message listener", e);
            }
        }
    }

    /**
     * 获取本地节点ID
     */
    public String getLocalNodeId() {
        return localNodeId;
    }

    /**
     * 获取附近的节点
     */
    public List<Node> getNearbyNodes() {
        if (bluetoothManager == null) {
            return Collections.emptyList();
        }
        return bluetoothManager.getNearbyNodes();
    }

    /**
     * 关闭应用
     */
    public void shutdown() {
        logger.info("Shutting down OfflineMessenger");
        stopBluetoothMesh();
        if (bluetoothManager != null) {
            bluetoothManager.close();
        }
        if (networkManager != null) {
            networkManager.close();
        }
        initialized = false;
    }

    /**
     * 消息类型枚举
     */
    public enum MessageType {
        TEXT,
        IMAGE,
        FILE,
        AUDIO,
        VIDEO
    }
}
