# Spring Could Alibaba 入门

## 简介
Spring Cloud Alibaba是阿里巴巴开源的一套微服务开发框架，它提供了一系列的组件和工具，帮助开发者快速构建分布式系统。

## 开发环境
> JDK: 8
> 
> SpringBoot: 2.6.13
> 
> spring-cloud-alibaba: 2021.0.5.0

## nacos-server下载
> nacos项目地址   https://github.com/alibaba/nacos
> 
> nacos-server   https://github.com/alibaba/nacos/releases/tag/2.3.2
> 
1. 选择nacos-server-2.3.2.zip下载
2. 解压后进入bin目录，执行命令：
```shell
startup.cmd -m standalone
```
3. 访问console中给出的地址例如：http://127.0.0.1:8848/nacos/index.html