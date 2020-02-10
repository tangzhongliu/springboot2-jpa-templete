## springboot 2.0 集成jpa模版

### 环境：

* 开发工具：Intellij IDEA 2019.2
* springboot: **2.1.6.RELEASE**
* jdk: **1.8.0_201**
* MariaDB: **10.3.14**
* Redis: **5.0.5**

### 集成：

* jpa ORM规范
* alibaba Druid 数据库连接池V1.1.9
* redis 缓存
* Spring Security 安全框架
* quartz-scheduler 定时计划
* commons-lang3 Apache工具集
* logback 日志框架（默认集成，不用额外引入依赖）

### 实现：

* RESTful API设计规范的接口
* Spring Security认证，授权
* 简单的基于表的CRUD处理的sample（jpa实现）
* Redis缓存sample
* 定时任务sample
* logback日志输出
* swagger3接口文档（以json文件实现接口文档）

### 数据结构：

* DB设计书: **/sql/DB设计书.xlsx**
* DDL    : **/sql/test_db_ddl.sql**
* DML    : **/sql/test_db_dml.sql**

### 测试：
* swagger文档入口: http://localhost:8090/api/v1/swagger3/index.html