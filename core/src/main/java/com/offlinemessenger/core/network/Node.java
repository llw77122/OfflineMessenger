package com.offlinemessenger.core.network;

import java.io.Serializable;

/**
 * 网络节点类
 */
public class Node implements Serializable {
    private String nodeId;
    private String deviceName;
    private int signalStrength;  // RSSI值
    private long lastSeen;
    private boolean isDirectNeighbor;

    public Node() {}

    public Node(String nodeId, String deviceName, int signalStrength) {
        this.nodeId = nodeId;
        this.deviceName = deviceName;
        this.signalStrength = signalStrength;
        this.lastSeen = System.currentTimeMillis();
        this.isDirectNeighbor = true;
    }

    // Getters and Setters
    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public int getSignalStrength() { return signalStrength; }
    public void setSignalStrength(int signalStrength) { this.signalStrength = signalStrength; }

    public long getLastSeen() { return lastSeen; }
    public void setLastSeen(long lastSeen) { this.lastSeen = lastSeen; }

    public boolean isDirectNeighbor() { return isDirectNeighbor; }
    public void setDirectNeighbor(boolean directNeighbor) { isDirectNeighbor = directNeighbor; }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId='" + nodeId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", signalStrength=" + signalStrength +
                ", isDirectNeighbor=" + isDirectNeighbor +
                '}';
    }
}
