# 极客时间 - 小马哥Java训练营

## user-platform

### 启动：

进入`user-web` 模块目录下，命令行运行:

```sh
mvn clean install
mvn tomcat7:run
```

------

## 第十一周作业

> 通过 Java 实现两种 (以及) 更多的一致性 Hash 算法 (可选) 实现服务节点动态更新

> org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHas hLoadBalance

### 实现

* `consistent-hashing` 模块增加一致性哈希的实现
* 将节点和虚拟阶段初始化到Hash环上
* 将请求路由到节点上，新增和删除节点，观察请求路由节点的变化情况

## 第十周作业

> 完善 @org.geektimes.projects.user.mybatis.annotation.Enable MyBatis 实现，尽可能多地注入 org.mybatis.spring.SqlSessionFactoryBean 中依赖的组件

### 实现

* `my-mybatis-spring` 模块中实现 `@EnableMybatis` 模块驱动
* 在`MyBatisBeanDefinitionRegistrar` 中初始化 `MapperScannerConfigurer` 和 `SqlSessionFactoryBean` 两个Bean，无需`@MapperScan`
* 自定义`Interceptor Bean` 以及 在`myabtis-config.xml`中手动配置的 `Interceptor` 都能生效

## 第九周作业

> Spring Cache 与 Redis 整合
> * 如何清除某个 Spring Cache 所有的 Keys 关联的对象
> * 如果 Redis 中心化方案，Redis + Sentinel
> * 如果 Redis 去中心化方案，Redis Cluster
> * 如何将 RedisCacheManager 与 @Cacheable 注解打通

### 实现

* 暂未实现

## 第八周作业

> 如何解决多个 WebSecurityConfigurerAdapter Bean 配置相互冲突的问题？
> * 提示：假设有两个 WebSecurityConfigurerAdapter Bean 定义，并且标注了不同的 @Order，其中一个关闭 CSRF，一个开启 CSRF，那么最终结果如何确定？
> * 背景：Spring Boot 场景下，自动装配以及自定义 Starter 方式非常流行，部分开发人员掌握了 Spring Security 配置方法，并且自定义了自己的实现，解决了 Order 的问题，然而会出现不确定配置因素。

### 实现

1. 只允许定义一个 `WebSecurityConfigurerAdapter`
2. 提供一个接口名字叫做 `com.acme.WebSecurityConfigurer` (与SpringSecurity相同)
3. 将唯一的 `WebSecurityConfigurerAdapter` 实现组合模式，组合的对象就是 `com.acme.WebSecurityConfigurer`，
   允许定义多个 `com.acme.WebSecurityConfigurer` Bean，在 `WebSecurityConfigurerAdapter`中迭代 `com.acme.WebSecurityConfigurer`
   Bean， 通过@Order 和 Ordered接口控制优先级。
4. 如果出现多个 `WebSecurityConfigurerAdapter`， 通过异常会警告来提示

> 如果是Spring Security 3.0版本，对应Spring Boot 1.x版本可以通过异常来提示。高如果是版本，只能通过警告来提醒。

```java
@Configuration
public class CompositeWebSecurityConfigrer extends WebSecurityConfigurerAdapter {

    @Autowired
    private List<MyWebSecurityConfigurer> webSecurityConfigurers;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.webSecurityConfigurers.forEach(c -> c.configure(http));
    }
}
```

## 第七周作业

> 使用 Spring Boot 或 Servlet 来实现一个整合 Gitee 或者 Github OAuth2 认证

### 接口

- Gitee第三方登录

> http://localhost:8080/gitee/login

- 回调地址

> http://localhost:8080/user/login

- 登录页

> http://localhost:8080/

- 首页

> http://localhost:8080/index

### 说明

* 在登录页增加`Gitee登录` 按钮，点击跳转到 `Gitee第三方登录`，然后redirect到`Gitee授权码登录`页面去申请授权
* 授权成功后，重定向到本地回调地址，根据`授权码 code` 获取`access token`，然后获取用户信息放到`session` 中完成登录。
* 拿到用户信息后需要自动创建本地用户，并与本地用户建立关联，完成用户创建。（未实现）

## 第六周作业

> 提供一套抽象 API 实现对象的序列化和反序列化 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache

### 接口

- 用户登录、登出

> http://localhost:8080/user/login

> http://localhost:8080/user/logout

> http://localhost:8080/user/list

### 说明

* 增加 `my-cache` 模块，实现JSR107 Cache API
* 增加 `InMemeoryCache` `JedisCache` `LettuceCache`
* 增加 `serialization` 抽象API， 默认实现 `DefaultSerialization`，采用JDK原生序列化策略
* 在 `HttpSessionFilter` 中判断用户是否登录，用户每次请求从 `Cache` 中获取用户 `Session` 属性，并设置到本地 `Session` 中，同步不同客户端修改的 `Session` 属性
* 登录、登出方法中在 `Session` 中添加、删除 `user` 对象， 在 `CacheableHttpSessionAttributeListener` 中监听 `Session` 属性的变化，将属性保存在 `Cache` 中

## 第五周作业

> * 修复本程序 org.geektimes.reactive.streams 包下
> * 继续完善 my-rest-client POST 方法
> * (可选) 读一下 Servlet 3.0 关于 Servlet 异步 AsyncContext

### 执行方法

* 测试方法在 `my-rest-client` 模块中测试包下的 `RestClientDemo` 中的 `testGet()`和`testPost()`
* 增加 `reactive-stream` 示例，简单测试 `reactive` 模型，在 `publish` 中检测通道是否取消或完成，不再继续发消息

### 说明

* 增加 `HttpPostInvocation` 请求处理，仅处理`application/json`格式的消息体
* 在 `http://localhost:8080/hello/world` 中打印 `POST` 请求的`Context-Type`，并输出请求体内容

## 第四周作业

> 完善 my dependency-injection 模块
> * 脱离 web.xml 配置实现 ComponentContext 自动初始化
> * 使用独立模块并且能够在 user-web 中运行成功

> 完善 my-configuration 模块
> * Config 对象如何能被 my-web-mvc 使用
> * 可能在 ServletContext 获取如何通过 ThreadLocal 获取

> 去提前阅读 Servlet 规范中 Security 章节（Servlet 容器安全）

### 接口

- Config

> http://localhost:8080/config/getInjectConfig

> http://localhost:8080/config/getServiceInjectConfig

### 说明

* 拆分`my-configuration` `my-dependency-inject` 两个独立模块
* `ServletContextListener` 无法通过 `ServletContainerInitializer#addListener`  的方式动态添加`Listener`
* 通过 `ServletContainerInitializer` 直接初始化 `ComponentContext` `ServletContextConfigSource` 两个组件
* 在 `ComponentContext` 和 `FrontControllerServlet` 中通过 `@ConfigProperty`  注解注入配置属性

### Config对象的获取问题

* `ConfigProviderResolver` 为单例模式，在`user-web`中依赖`my-configuration`，直接通过 `ConfigProviderResolver.instance()` 实例化获取
* 在`my-web-mvc` 中不能直接依赖 `my-configuration`，可依赖`microprofile-config-api`。 然后将`Config` 对象注册为JNDI组件使用，这样`my-web-mvc`
  就需要再去依赖`my-dependency-inject`。 如果想要减少依赖。可以将`Config`对象添加到`ServletContext`中，这样在`HttpServletRequest`中获取`ServletContext`
  ，然后得到`Config`对象。
* 个人认为`ThreadLocal`不是个好的方案，`Config`为全局单例对象，放入`ThreadLocal`中就成了线程级别单例对象。

## 第三周作业

> 需求一（必须）
> * 整合 https://jolokia.org/
> * 实现一个自定义 JMX MBean，通过 Jolokia 做 Servlet 代理

> 需求二（选做）
> * 继续完成 Microprofile config API 中的实现
> * 扩展 org.eclipse.microprofile.config.spi.ConfigSource 实现，包括 OS 环境变量，以及本地配置文件
> * 扩展 org.eclipse.microprofile.config.spi.Converter 实现，提供 String 类型到简单类型
> * 通过 org.eclipse.microprofile.config.Config 读取当前应用名称: 应用名称 property name = "application.name"

### 接口

- jolokia

> http://localhost:8080/jolokia/read/java.lang:type=Memory/HeapMemoryUsage

- User

> http://localhost:8080/jolokia/read/cn.yufenghui.lesson.user.management:type=User

- Config

> http://localhost:8080/jolokia/read/cn.yufenghui.lesson.config:type=Config

> http://localhost:8080/config/getConfig?name=application.name

> http://localhost:8080/config/getConfig?name=user.age

> http://localhost:8080/config/getConfig?name=Path

### 说明

> * Jolokia 在`MBeanServerListener` 中初始化自定义的MBean
> * MicroProfile Config 在 `cn.yufenghui.lession.configuration.microprofile.config` 包下
> * 定义了 `OsEnvConfigSource`, `JavaSystemConfigSource`, `LocalFileConfigSource` 三个 `ConfigSource` 实现
> * 将 `ExampleConfigProviderResolver` 注册为JNDI资源，在`ConfigController` 中使用
> * 本地配置文件在 `resources/config.properties`，使用UTF-8编码
> * 增加MicroProfile Config MBean，并通过Jolokia代理访问

## 第二周作业

> 通过课堂上的简易版依赖注入和依赖查找，实现用户注册功能

> 通过 UserService 实现用户注册注册用户需要校验
> * Id：必须大于 0 的整数
> * 密码：6-32 位 电话号码: 采用中国大陆方式（11 位校验）

### 接口

- UserListController

> http://localhost:8080/user/list

- UserRegisterController

> http://localhost:8080/user/register

### 说明
> * `ComponentContext` 增加依赖注入功能
> * 降低Hibernate JPA版本 `5.2.18.Final`，Validator版本`5.4.1.Final`，以适配Tomcat7
> * 采用factory形式注入EntityManager，Validator等第三方接口
> * 用户注册中，限定密码最小6位，可以增加请求参数password进行验证

```xml
<Resource name="bean/EntityManager" auth="Container"
          type="javax.persistence.EntityManager"
          factory="cn.yufenghui.lession.factory.MyEntityManagerFactory"
/>

<Resource name="bean/validator" auth="Container"
          type="javax.validation.Validator"
          factory="cn.yufenghui.lession.factory.MyValidatorFactory"
/>
```

## 第一周作业

> * 通过自研 Web MVC 框架实现（可以自己实现）一个用户注册，forward 到一个成功的页面（JSP 用法）/register
> * 通过 Controller -> Service -> Repository 实现（数据库实现）
> * （非必须）JNDI 的方式获取数据库源（DataSource），在获取 Connection

### 接口

- PageController

> http://localhost:8080/hello/world

- RestController

> http://localhost:8080/hello/world2

### 测试用例

> UserRepositoryTest

### 说明

> * 在设计中，采用`ComponentContext`方式，以Bean的形式获取`DBConnectionManager`
> * 在`ComponentContextInitializerListener`中初始化`ComponentContext`
> * 在`DBInitializerListener`初始化数据，供`HelloController`测试使用
> * 通过dbType设置数据源类型

```xml
<!-- bean -->
<Resource name="bean/DBConnectionManager" auth="Container"
          type="cn.yufenghui.lession.user.db.DBConnectionManager"
          factory="org.apache.naming.factory.BeanFactory"
          dbType="jndi"
          />
```

> * JNDI数据源在maven tomcat插件下配置不成功，放弃使用。
> * 在设计中，初始化`DBConnectionManager`的时候，可以指定数据源类型，jdbc 或者 jndi

```java
public DBConnectionManager(String type, String connStr){
    this.type = type;
    this.connStr = connStr;
}
```
