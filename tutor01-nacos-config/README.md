# nacos-config 入门
## 简介
Nacos Config 是一个用于存储和管理分布式系统配置的工具。它可以帮助开发人员将配置信息集中存储在一个地方，并在需要时轻松获取和更新这些配置。

简单来说，Nacos Config 就像是一个配置中心，它可以存储各种应用程序的配置信息，例如数据库连接信息、服务器地址、端口号、用户名、密码等。开发人员可以通过 Nacos Config 来管理这些配置信息，而不需要在每个应用程序中单独维护配置文件。

Nacos Config 还支持配置的动态更新，当配置信息发生变化时，它可以自动通知应用程序获取最新的配置信息。这使得开发人员可以更加方便地管理和更新配置信息，而不需要重新启动应用程序。

1. 通过 https://start.aliyun.com/ 创建项目，添加spring web和 nacos config依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```
2. 项目创建完成后nacosconfig和web包下都是样例代码
3. 添加最简单的配置项，还有其他配置可以查看applciation.properties
```xml
# nacos server地址
spring.cloud.nacos.server-addr=127.0.0.1:8848
# 配置中心中的配置文件
spring.config.import=nacos:nacos-config.properties?refresh=true
```
4. 启动nacos server 在 127.0.0.1:8848 配置列表添加配置
> Data Id 文件名：nacos-config.properties

> Group 组名：默认即可

> 配置内容： 
```xml
lgd.carry=ame1
lgd.mid=maybe1
lgd.offlane=chalice
```
5. 通过@Value注解获取配置文件内容
```java
@RestController
@RefreshScope
public class ValueController {
    @Value("${lgd.carry}")
    private String lgdcarry;
    @GetMapping("lgd/carry")
    public String test() {
        return lgdcarry;
    }
}
```
6. 通过@ConfigurationProperties注解获取配置文件内容
```java
@ConfigurationProperties(prefix = "lgd")
@Component
public class Lgd {
    private String mid;
    private String offlane;
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getOfflane() {
        return offlane;
    }
    public void setOfflane(String offlane) {
        this.offlane = offlane;
    }
}
```
```java
@RestController
@RefreshScope
public class ConfigurationPropertiesController {
    @Autowired
    private Lgd lgd;
    @GetMapping("lgd/mid")
    public String test() {
        return lgd.getMid();
    }
}
```
7. @RefreshScope注解的作用是配置文件热更新，即更改配置中心的配置内容后查询即更改后的数据

8. 其他配置项


| 配置项 | Key | 默认值 | 说明 |
|---|---|---|---|
|服务端地址|spring.cloud.nacos.config.server-addr||Nacos Server 启动监听的 ip 地址和端口|
|配置对应的 DataId|spring.cloud.nacos.config.name||先取 prefix，再取 name，最后取 spring.application.name|
|配置对应的 DataId|spring.cloud.nacos.config.prefix||先取 prefix，再取 name，最后取 spring.application.name|
|配置内容编码|spring.cloud.nacos.config.encode||读取的配置内容对应的编码|
|GROUP|spring.cloud.nacos.config.group|DEFAULT_GROUP|配置对应的组|
|文件扩展名|spring.cloud.nacos.config.fileExtension|properties|配置项对应的文件扩展名，目前支持 properties 和 yaml(yml)|
|获取配置超时时间|spring.cloud.nacos.config.timeout|3000|客户端获取配置的超时时间(毫秒)|
|接入点|spring.cloud.nacos.config.endpoint||地域的某个服务的入口域名，通过此域名可以动态地拿到服务端地址|
|命名空间|spring.cloud.nacos.config.namespace||常用场景之一是不同环境的配置的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等|
|AccessKey|spring.cloud.nacos.config.accessKey||当要上阿里云时，阿里云上面的一个云账号名|
|SecretKey|spring.cloud.nacos.config.secretKey||当要上阿里云时，阿里云上面的一个云账号密码|
|Nacos Server 对应的 context path|spring.cloud.nacos.config.contextPath||Nacos Server 对外暴露的 context path|
|集群|spring.cloud.nacos.config.clusterName||配置成 Nacos 集群名称|
|共享配置|spring.cloud.nacos.config.sharedDataids||共享配置的 DataId，","分割|
|共享配置动态刷新|spring.cloud.nacos.config.refreshableDataids||共享配置中需要动态刷新的 DataId，","分割|
|自定义 Data Id 配置|spring.cloud.nacos.config.extConfig||属性是个集合，内部由 ConfigPOJO 组成。Config 有 3 个属性，分别是 dataId，group 以及 refresh|