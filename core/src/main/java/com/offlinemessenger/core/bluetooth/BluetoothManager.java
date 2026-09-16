package com.offlinemessenger.core.bluetooth;

import com.offlinemessenger.core.messaging.Message;
import com.offlinemessenger.core.messaging.MessageListener;
import com.offlinemessenger.core.network.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 蓝牙管理器
 * 管理蓝牙设备的扫描、连接和数据传输
 */
public class BluetoothManager {
    private static final Logger logger = LoggerFactory.getLogger(BluetoothManager.class);

    private String localNodeId;
    private boolean isScanning;
    private List<Node> discoveredNodes;
    private MessageListener messageListener;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothManager(String localNodeId) {
        this.localNodeId = localNodeId;
        this.discoveredNodes = new CopyOnWriteArrayList<>();
        this.isScanning = false;
        this.bluetoothAdapter = initializeBluetoothAdapter();
        logger.info("BluetoothManager initialized for node: {}", localNodeId);
    }

    /**
     * 初始化蓝牙适配器
     */
    private BluetoothAdapter initializeBluetoothAdapter() {
        // TODO: 实现跨平台蓝牙适配器初始化
        logger.info("Bluetooth adapter initialized");
        return new BluetoothAdapter();
    }

    /**
     * 开始扫描蓝牙设备
     */
    public void startScanning() {
        if (isScanning) {
            logger.warn("Bluetooth scanning already started");
            return;
        }

        isScanning = true;
        logger.info("Starting Bluetooth device scanning");

        // 在后台线程中执行扫描
        new Thread(() -> {
            while (isScanning) {
                try {
                    scanDevices();
                    Thread.sleep(5000);  // 每5秒扫描一次
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "BluetoothScannerThread").start();
    }

    /**
     * 停止扫描蓝牙设备
     */
    public void stopScanning() {
        isScanning = false;
        logger.info("Stopped Bluetooth device scanning");
    }

    /**
     * 扫描附近的蓝牙设备
     */
    private void scanDevices() {
        // TODO: 实现实际的蓝牙设备扫描逻辑
        logger.debug("Scanning for Bluetooth devices");
    }

    /**
     * 发送消息
     *
     * @param message 要发送的消息
     */
    public void sendMessage(Message message) {
        if (discoveredNodes.isEmpty()) {
            logger.warn("No discovered nodes available");
            return;
        }

        // 尝试直接发送给接收者，或转发给邻接节点
        Node targetNode = null;
        for (Node node : discoveredNodes) {
            if (node.getNodeId().equals(message.getRecipient())) {
                targetNode = node;
                break;
            }
        }

        if (targetNode != null) {
            // 直接发送
            sendMessageToNode(message, targetNode);
        } else {
            // 广播转发
            broadcastMessage(message);
        }
    }

    /**
     * 直接发送消息给指定节点
     */
    private void sendMessageToNode(Message message, Node node) {
        logger.info("Sending message {} to node {}", message.getId(), node.getNodeId());
        // TODO: 实现实际的蓝牙消息发送
    }

    /**
     * 广播转发消息
     */
    private void broadcastMessage(Message message) {
        message.incrementHops();
        if (message.getHops() > 10) {  // 最多转发10跳
            logger.warn("Message hop limit exceeded");
            return;
        }
        logger.info("Broadcasting message {} to {} neighbors", message.getId(), discoveredNodes.size());
        for (Node neighbor : discoveredNodes) {
            sendMessageToNode(message, neighbor);
        }
    }

    /**
     * 设置消息监听器
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    /**
     * 处理接收到的消息
     */
    protected void handleReceivedMessage(Message message) {
        if (messageListener != null) {
            messageListener.onMessageReceived(message);
        }
    }

    /**
     * 获取附近的节点
     */
    public List<Node> getNearbyNodes() {
        return new ArrayList<>(discoveredNodes);
    }

    /**
     * 关闭蓝牙管理器
     */
    public void close() {
        stopScanning();
        discoveredNodes.clear();
        logger.info("BluetoothManager closed");
    }
}
