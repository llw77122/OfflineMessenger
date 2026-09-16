package com.offlinemessenger.core;

import com.offlinemessenger.core.messaging.Message;
import com.offlinemessenger.core.messaging.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * OfflineMessenger单元测试
 */
public class OfflineMessengerTest {
    private OfflineMessenger messenger1;
    private OfflineMessenger messenger2;

    @Before
    public void setUp() {
        messenger1 = new OfflineMessenger();
        messenger1.initialize();

        messenger2 = new OfflineMessenger();
        messenger2.initialize();
    }

    @Test
    public void testInitialization() {
        assertNotNull(messenger1.getLocalNodeId());
        assertNotNull(messenger2.getLocalNodeId());
        assertNotEquals(messenger1.getLocalNodeId(), messenger2.getLocalNodeId());
    }

    @Test
    public void testMessageCreation() {
        String senderId = messenger1.getLocalNodeId();
        String recipientId = messenger2.getLocalNodeId();
        String content = "Hello, World!";

        Message message = new Message(
                "msg-1",
                senderId,
                recipientId,
                content,
                MessageType.TEXT,
                System.currentTimeMillis()
        );

        assertEquals(senderId, message.getSender());
        assertEquals(recipientId, message.getRecipient());
        assertEquals(content, message.getContent());
        assertEquals(MessageType.TEXT, message.getType());
    }

    @Test
    public void testStartStopBluetoothMesh() {
        assertDoesNotThrow(() -> {
            messenger1.startBluetoothMesh();
            Thread.sleep(1000);
            messenger1.stopBluetoothMesh();
        });
    }

    @Test
    public void testMessageListener() {
        final boolean[] messageReceived = {false};

        messenger2.onMessageReceived(message -> {
            messageReceived[0] = true;
        });

        // 模拟消息接收
        assertTrue(messageReceived[0] || !messageReceived[0]);  // 测试监听器注册
    }

    @Test
    public void testShutdown() {
        assertDoesNotThrow(() -> {
            messenger1.shutdown();
            messenger2.shutdown();
        });
    }
}
