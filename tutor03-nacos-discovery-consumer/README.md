# nacos discovery 入门

1. 创建项目，引入依赖

**首先，创建一个Spring Boot项目，并在pom.xml中添加必要的依赖。需要注意的是，较新版本的nacos-discovery已经移除了对ribbon的依赖。要检查nacos-discovery是否包含ribbon，如果没有，则需要引入负载均衡依赖，如loadbalancer或ribbon。缺少任何负载均衡依赖会导致后续调用出现java.net.UnknownHostException**

> <spring-boot.version>2.3.12.RELEASE</spring-boot.version>

> <spring-cloud-alibaba.version>2.2.10-RC1</spring-cloud-alibaba.version>

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

2. 添加配置
```xml
server.port=9001
spring.application.name=tutor03-nacos-discovery-consumer
spring.cloud.nacos.server-addr=127.0.0.1:8848
```

3. 添加@EnableDiscoveryClient注解和RestTemplate

在主应用程序类上添加@EnableDiscoveryClient注解，以启用服务发现功能。同时，定义一个RestTemplate的Bean，并使用@LoadBalanced注解，使其具备负载均衡的能力

**如果没有任何负载均衡依赖，后续调用会报错java.net.UnknownHostException**

```java
@SpringBootApplication
@EnableDiscoveryClient
public class Tutor03NacosDiscoveryConsumerApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Tutor03NacosDiscoveryConsumerApplication.class, args);
    }

}
```
4. controller代码 

在Controller中，使用RestTemplate调用其他服务的方法。
```java
@RestController
public class ConsumerController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    @GetMapping("consumer/hello")
    public String test() {
        return restTemplate.getForObject("http://tutor02-nacos-discovery/provider/hello", String.class);
    }
    
}
```
5. 把nacos server和项目2启动起来，再启动本项目后，访问 http://localhost:9001/consumer/hello


## 除了使用RestTemplate，还可以使用OpenFeign来实现类似的功能。

1. 引入openfeign依赖

可以看到openfeign里面有负载均衡依赖ribbon，所以openfeign具有负载均衡功能

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

2. 编写feign声明式接口
```java
@FeignClient("tutor02-nacos-discovery")
public interface ConsumerFeign {
    @GetMapping("provider/hello")
    public String hello();
}
```

3. 添加注解@EnableFeignClients
```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Tutor03NacosDiscoveryConsumerApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Tutor03NacosDiscoveryConsumerApplication.class, args);
    }

}
```

4. 控制类添加调用feign接口的代码
```java
@RestController
public class ConsumerController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    @GetMapping("consumer/hello")
    public String test() {
        return restTemplate.getForObject("http://tutor02-nacos-discovery/provider/hello", String.class);
    }

    @Autowired
    private ConsumerFeign consumerFeign;

    @GetMapping("consumer/test")
    public String feignTest() {
        return consumerFeign.hello();
    }
    
    
}
```
5. 重启本项目，访问 http://localhost:9001/consumer/test


## 其他配置项
|配置项|Key|默认值|说明|
|---|---|---|---|
|服务端地址|spring.cloud.nacos.discovery.server-addr||Nacos Server 启动监听的 ip 地址和端口|
|服务名|spring.cloud.nacos.discovery.service|${spring.application.name}|注册的服务名|
|权重|spring.cloud.nacos.discovery.weight|1|取值范围 1 到 100，数值越大，权重越大|
|网卡名|spring.cloud.nacos.discovery.network-interface||当 IP 未配置时，注册的 IP 为此网卡所对应的 IP 地址，如果此项也未配置，则默认取第一块网卡的地址|
|注册的 IP 地址|spring.cloud.nacos.discovery.ip||优先级最高|
|注册的端口|spring.cloud.nacos.discovery.port|-1|默认情况下不用配置，会自动探测|
|命名空间|spring.cloud.nacos.discovery.namespace||常用场景之一是不同环境的注册的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等|
|AccessKey|spring.cloud.nacos.discovery.access-key||当要上阿里云时，阿里云上面的一个云账号名|
|SecretKey|spring.cloud.nacos.discovery.secret-key||当要上阿里云时，阿里云上面的一个云账号密码|
|Metadata|spring.cloud.nacos.discovery.metadata||使用Map格式配置，用户可以根据自己的需要自定义一些和服务相关的元数据信息|
|日志文件名|spring.cloud.nacos.discovery.log-name|||
|集群|spring.cloud.nacos.discovery.cluster-name|DEFAULT|Nacos集群名称|
|接入点|spring.cloud.nacos.discovery.endpoint||地域的某个服务的入口域名，通过此域名可以动态地拿到服务端地址|
|是否集成 Ribbon|ribbon.nacos.enabled|true|一般都设置成true即可|
|是否开启 Nacos Watch|spring.cloud.nacos.discovery.watch.enabled|||