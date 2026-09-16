package com.offlinemessenger.core.messaging;

import java.io.Serializable;

/**
 * 消息类
 */
public class Message implements Serializable {
    private String id;
    private String sender;
    private String recipient;
    private String content;
    private MessageType type;
    private long timestamp;
    private boolean encrypted;
    private int hops;  // 用于网状网络的跳数

    public Message() {}

    public Message(String id, String sender, String recipient, String content,
                   MessageType type, long timestamp) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
        this.encrypted = false;
        this.hops = 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isEncrypted() { return encrypted; }
    public void setEncrypted(boolean encrypted) { this.encrypted = encrypted; }

    public int getHops() { return hops; }
    public void setHops(int hops) { this.hops = hops; }
    public void incrementHops() { this.hops++; }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", hops=" + hops +
                '}';
    }
}
