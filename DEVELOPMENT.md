# 开发指南

## 项目架构

### 核心模块 (core)

#### 1. 消息模块 (messaging)
- `Message.java` - 消息数据模型
- `MessageType.java` - 消息类型枚举
- `MessageListener.java` - 消息监听器接口

**职责**:
- 定义消息结构
- 提供消息序列化/反序列化
- 实现消息加密/解密

#### 2. 网络模块 (network)
- `Node.java` - 网络节点表示
- `NetworkManager.java` - 网络拓扑和路由管理

**职责**:
- 管理邻接节点
- 实现消息路由算法
- 处理节点发现和超时
- 消息去重缓存

#### 3. 蓝牙模块 (bluetooth)
- `BluetoothAdapter.java` - 跨平台蓝牙适配器
- `BluetoothManager.java` - 蓝牙设备管理

**职责**:
- 扫描蓝牙设备
- 管理蓝牙连接
- 处理数据传输
- 实现蓝牙网状网络

#### 4. 加密模块 (crypto)
- `CryptoManager.java` - 加密算法管理

**职责**:
- AES加密/解密
- RSA密钥交换
- 数字签名
- 哈希计算

### 应用层

- `OfflineMessenger.java` - 主应用类，提供统一接口

## 开发流程

### 1. 环境设置

```bash
# 克隆仓库
git clone https://github.com/llw77122/OfflineMessenger.git
cd OfflineMessenger

# 安装依赖
mvn clean install
```

### 2. 开发新功能

1. 创建特性分支
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. 实现功能并编写测试
   ```bash
   # 在 core/src/main/java 中添加代码
   # 在 core/src/test/java 中添加测试
   ```

3. 运行测试
   ```bash
   mvn test
   ```

4. 提交并推送
   ```bash
   git add .
   git commit -m "feat: add your feature description"
   git push origin feature/your-feature-name
   ```

### 3. 代码规范

- 使用驼峰命名法
- 为所有公共类和方法添加JavaDoc注释
- 遵循SOLID原则
- 确保单元测试覆盖率 > 80%

## 实现优先级

### Phase 1: 核心网络 ⏳ 进行中
- [x] 消息数据模型
- [x] 网络节点管理
- [ ] 基本消息路由
- [ ] 节点发现

### Phase 2: 蓝牙实现
- [ ] 跨平台蓝牙初始化
- [ ] 设备扫描
- [ ] 连接管理
- [ ] 数据传输

### Phase 3: 加密通信
- [ ] AES加密实现
- [ ] RSA密钥交换
- [ ] 数字签名
- [ ] 密钥管理

### Phase 4: 存储层
- [ ] SQLite集成
- [ ] 消息持久化
- [ ] 离线消息队列
- [ ] 数据库迁移

### Phase 5: UI开发
- [ ] Android UI
- [ ] iOS支持
- [ ] 桌面UI

## 常见问题

### Q: 如何测试蓝牙功能？
A: 目前使用模拟器和单元测试。后期需要真实设备测试。

### Q: 消息格式是什么？
A: 使用JSON序列化，支持加密传输。详见Message类。

### Q: 最大传输距离是多少？
A: 蓝牙通常5-10米，通过网状网络可扩展范围。

## 资源

- [Android蓝牙文档](https://developer.android.com/guide/topics/connectivity/bluetooth)
- [BlueZ Linux蓝牙栈](http://www.bluez.org/)
- [Bouncy Castle加密库](https://www.bouncycastle.org/)
