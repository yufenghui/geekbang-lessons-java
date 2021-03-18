# 极客时间 - 小马哥Java训练营

## user-platform

### 启动：

进入`user-web` 模块目录下，命令行运行:
```sh
mvn clean install
mvn tomcat7:run
```

------

## V4

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


## V3

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

## V2

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

## V1

### 接口

- PageController

> http://localhost:8080/hello/world

- RestController

> http://localhost:8080/hello/world2

### 测试用例

> UserRepositoryTest

###  说明

> * JNDI数据源在maven tomcat插件下配置不成功，放弃使用。
> * 在设计中，初始化`DBConnectionManager`的时候，可以指定数据源类型，jdbc 或者 jndi

```java
public DBConnectionManager(String type, String connStr){
    this.type = type;
    this.connStr = connStr;
}
```
