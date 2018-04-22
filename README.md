# Wilamr Admin

项目首页：http://git.wilmar.cn/YinGuowei/WilmarAdmin

## 初始安装运行

Clone 运行
```
git clone --depth=1 http://git.wilmar.cn/YinGuowei/WilmarAdmin.git
cd WilmarAdmin
chmod +x mvnw
./mvnw spring-boot:run
```

> 内置集成了 MySQL 和 H2 两种数据库驱动供使用，如果需要添加其他关系型数据库，在 Spring Boot pom 内添加对应驱动即可

### 设置数据库

修改 application.properties :

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
```

## 开发一个模型

### Domain model

### DAO

### Service

### Controller

### Web Pages

## 设置关联关系