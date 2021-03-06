# 极客时间 - 小马哥Java训练营

## user-platform

### 启动：

进入`user-web` 模块目录下，命令行运行:
> mvn tomcat7:run

### 项目运行

- PageController

> http://localhost:8080/hello/world

- RestController

> http://localhost:8080/hello/world2

### 测试用例

> UserRepositoryTest

### V1 说明

> * JNDI数据源在maven tomcat插件下配置不成功，放弃使用。
> * 在设计中，初始化`DBConnectionManager`的时候，可以指定数据源类型，jdbc 或者 jndi

```java
public DBConnectionManager(String type, String connStr){
    this.type = type;
    this.connStr = connStr;
}
```

### V2 说明

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