package com.offlinemessenger.core.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网络管理器
 * 管理网状网络拓扑和消息路由
 */
public class NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);
    private static final long NODE_TIMEOUT = 60000;  // 60秒超时

    private String localNodeId;
    private Map<String, Node> neighbors;  // 邻接点
    private Map<String, Long> messageCache;  // 用于去重

    public NetworkManager(String localNodeId) {
        this.localNodeId = localNodeId;
        this.neighbors = new ConcurrentHashMap<>();
        this.messageCache = new ConcurrentHashMap<>();
        logger.info("NetworkManager initialized for node: {}", localNodeId);
    }

    /**
     * 添加邻接节点
     */
    public void addNeighbor(Node node) {
        if (node == null || node.getNodeId().equals(localNodeId)) {
            return;
        }
        neighbors.put(node.getNodeId(), node);
        logger.debug("Neighbor added: {} (signal: {}dBm)", node.getNodeId(), node.getSignalStrength());
    }

    /**
     * 移除邻接节点
     */
    public void removeNeighbor(String nodeId) {
        neighbors.remove(nodeId);
        logger.debug("Neighbor removed: {}", nodeId);
    }

    /**
     * 获取所有邻接节点
     */
    public List<Node> getNeighbors() {
        return new ArrayList<>(neighbors.values());
    }

    /**
     * 清理超时的节点
     */
    public void cleanupTimeoutNodes() {
        long now = System.currentTimeMillis();
        neighbors.entrySet().removeIf(entry -> {
            Node node = entry.getValue();
            boolean timeout = now - node.getLastSeen() > NODE_TIMEOUT;
            if (timeout) {
                logger.debug("Node timeout: {}", entry.getKey());
            }
            return timeout;
        });
    }

    /**
     * 检查消息是否已缓存（用于去重）
     */
    public boolean isCached(String messageId) {
        return messageCache.containsKey(messageId);
    }

    /**
     * 缓存消息ID
     */
    public void cacheMessage(String messageId) {
        messageCache.put(messageId, System.currentTimeMillis());
    }

    /**
     * 清理消息缓存
     */
    public void cleanupMessageCache() {
        long now = System.currentTimeMillis();
        messageCache.entrySet().removeIf(entry -> now - entry.getValue() > NODE_TIMEOUT);
    }

    /**
     * 关闭网络管理器
     */
    public void close() {
        neighbors.clear();
        messageCache.clear();
        logger.info("NetworkManager closed");
    }
}
