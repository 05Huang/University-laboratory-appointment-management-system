# 实验室管理系统 🔬

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.4.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JDK](https://img.shields.io/badge/JDK-1.8-orange.svg)](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-5.7+-blue.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-最新版本-red.svg)](https://redis.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

_✨ 一款现代化高效实验室管理系统 ✨_


</div>

## 🌟 功能特点

- 🔐 多角色用户管理（管理员/教师/学生）
- 📅 智能实验室预约系统
- 📊 设备管理与追踪
- 📈 使用统计生成
- 🔔 人脸录入与识别
- 🎨 现代化响应式界面

## 🚀 快速开始

### 前置条件

- JDK 1.8及以上版本
- Maven 3.x
- MySQL 5.7及以上版本
- Redis

### 安装步骤

1. 克隆仓库
```bash
git clone https://github.com/your-username/laboratory.git
```

2. 配置数据库
```bash
# 从/sql目录导入SQL文件
mysql -u 你的用户名 -p 你的数据库名 < sql/laboratory.sql
```

3. 配置application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/你的数据库名
spring.datasource.username=你的用户名
spring.datasource.password=你的密码
```

4. 运行应用
```bash
mvn spring-boot:run
```

5. 访问应用
```
http://localhost:8080/index
```
