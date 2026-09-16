# OfflineMessenger

一个跨平台的离线消息应用，支持蓝牙网状网络和多人通信。

## 功能特性

- ✅ **无网络通信** - 在没有互联网的情况下发送消息
- ✅ **蓝牙网状网络** - 通过蓝牙实现多跳转发
- ✅ **多人通信** - 支持群组消息和点对点通信
- ✅ **跨平台** - 支持 Android、iOS、Windows、Linux、macOS
- ✅ **离线存储** - 本地消息历史记录
- ✅ **加密通信** - 端到端消息加密

## 项目结构

```
OfflineMessenger/
├── core/                    # 核心库
│   ├── messaging/          # 消息处理模块
│   ├── network/            # 网络模块
│   ├── bluetooth/          # 蓝牙模块
│   └── crypto/             # 加密模块
├── android/                # Android 应用
├── ios/                    # iOS 应用 (跨平台支持)
├── desktop/                # 桌面应用
└── tests/                  # 测试
```

## 技术栈

- **主语言**: Java 11+
- **跨平台框架**: LibGDX / JavaFX
- **蓝牙库**: BlueCove (Windows/Linux), Android Bluetooth API
- **加密**: Bouncy Castle
- **网络**: Netty (可选)
- **数据存储**: SQLite

## 快速开始

### 环境要求

- JDK 11 或更高版本
- Maven 3.6+
- 蓝牙硬件支持

### 编译构建

```bash
mvn clean install
```

### 运行应用

```bash
# Android
mvn -pl android install

# Desktop
mvn -pl desktop exec:java
```

## 核心模块说明

### 1. 消息模块 (Messaging)
- 消息序列化/反序列化
- 消息加密
- 消息存储
- 消息路由

### 2. 网络模块 (Network)
- 节点发现
- 网状网络拓扑
- 消息转发
- 连接管理

### 3. 蓝牙模块 (Bluetooth)
- 蓝牙设备扫描
- 蓝牙连接管理
- 数据传输
- 信号强度检测

### 4. 加密模块 (Crypto)
- AES 加密
- RSA 密钥交换
- 数字签名

## API 示例

```java
// 初始化
OfflineMessenger messenger = new OfflineMessenger();
messenger.initialize();

// 启动蓝牙网络
messenger.startBluetoothMesh();

// 发送消息
messenger.sendMessage(
    recipientId,
    "Hello, friend!",
    MessageType.TEXT
);

// 监听消息
messenger.onMessageReceived(message -> {
    System.out.println("收到来自 " + message.getSender() + ": " + message.getContent());
});

// 创建群组
messenger.createGroup("Group Name", memberIds);

// 发送群组消息
messenger.sendGroupMessage(groupId, "Group message");
```

## 开发计划

- [ ] Phase 1: 核心网络模块
- [ ] Phase 2: 蓝牙网状网络实现
- [ ] Phase 3: 加密通信
- [ ] Phase 4: Android UI
- [ ] Phase 5: iOS 支持
- [ ] Phase 6: 桌面应用
- [ ] Phase 7: 测试和优化

## 贡献指南

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License
