package com.offlinemessenger.core.messaging;

/**
 * 消息类型枚举
 */
public enum MessageType {
    TEXT("text/plain"),
    IMAGE("image/*"),
    FILE("application/octet-stream"),
    AUDIO("audio/*"),
    VIDEO("video/*");

    private final String mimeType;

    MessageType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
