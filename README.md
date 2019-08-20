# 基于hadoop-yarn的在离线混部资源管理系统

## 基本信息
授权协议：GPL  
操作系统：Centos  
开发语言：JAVA  
开发单位： 北京航空航天大学  
项目网站：  https://xueshiqing.github.io/
项目源码下载： https://gitee.com/xueshiqing/hadoop-yarn-dag-service

## 项目描述
基于Hadoop-yarn的在离线混部资源管理系统提供对在线任务和离线任务在同一个集群下的混合调度，由北京航空航天大学倾力打造。
云计算是互联网时代信息基础设施的重要形态和信息技术发展的重要模式。随着云计算数据中心承载业务种类越来越丰富，从结构上，现有调度框架往往采
用两层调度机制，资源管理层侧重管理资源使用信息，并在数据中心资源池层面分配资源；不同任务负载拥有独立的任务调度器，造成了一定的资源浪费。为了
更有效的利用数据中心资源，资源调度正呈现多粒度，多负载混合调度的特征。如Mesos，Yarn 等，通过不同粒度及负载的混合调度，实现资源的有效利用。
该项目就是在上述背景下进行研发，主要面向大规模数据中心，通过统一的资源管理和调度实现对在线离线任务的混合调度。

## 技术架构
1. 后端
  - 基础框架：Hadoop-yarn，docker，protobuf
  - 日志打印：log4j
  - 其他：fastjson，lombok等

2. 开发环境
  - 语言：JAVA
  - IDE：IDEA
  - 依赖管理：Maven

## 功能模块
- 在线离线任务混合调度
- 在线任务关键组件分析  
- 基于关键组件分析的离线避让调度

## 后台开发环境和依赖
- java
- maven
- Hadoop-yarn
- docker
- protobuf

## 项目下载和运行
- 拉取项目代码
```
git clone url
```
- 编译
```
mvn clean;
mvn package;
```
- 运行
```
cd /HADOOP_HOME/bin
./start-all.sh
```
## 附件下载