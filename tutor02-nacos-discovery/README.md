# nacos discovery 入门
## 简介
Nacos Discovery 是 Spring Cloud Alibaba 提供的服务注册与发现组件，它可以帮助您将服务自动注册到 Nacos 服务端，并能够动态感知和刷新某个服务实例的服务列表。除此之外，Nacos Discovery 也将服务实例自身的一些元数据信息（例如 host、port、健康检查 URL、主页等）注册到 Nacos。

1. 通过 [start.aliyun.com](https://start.aliyun.com/) 创建项目，引入三个依赖，作为注册的话只需要nacos-discovery依赖即可，但是此服务还作为服务提供者所以引入了web依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
2. 配置 Nacos Discovery，在application.properties添加配置
```xml
# 应用名
spring.application.name=tutor02-nacos-discovery
# nacos server地址
spring.cloud.nacos.server-addr=127.0.0.1:8848

server.port=8001

# actuator端口 
management.server.port=8002
# jmx和web端点暴露 
management.endpoints.jmx.exposure.include=*
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

3. 启动nacos server
4. 添加@EnableDiscoveryClient注解
```java
@SpringBootApplication
@EnableDiscoveryClient
public class Tutor02NacosDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tutor02NacosDiscoveryApplication.class, args);
    }

}
```
5. 启动项目，访问 127.0.0.1:8848 在左侧的服务管理中点击服务列表观察到tutor02-nacos-discovery实例即成功

6. 编写一个web服务作为提供者给别人调用
```java
@RestController
public class ProviderController {
    @GetMapping("/provider/hello")
    public String hello() {
        return "nacos provider";
    }
}
```