# 创建 Spring Boot 项目
依赖：

web
jpa
h2
thymeleaf
lombok
devtools
data-rest

security
cache

## 配置属性

禁用 Thymeleaf 缓存（开发环境）
```properties
spring.thymeleaf.cache=false
```
Log 等级
```properties
logging.level.cn.wilmar=debug
```
数据库配置
```properties
spring.jpa.database=h2
spring.jpa.show-sql=true
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
```
去掉一个告警
```properties
spring.jpa.open-in-view=false
```
打开控制台
```properties
spring.h2.console.enabled=true
```
修改默认的 RestRepositoryResource 路径
```properties
spring.data.rest.base-path=/api
```

测试
```java
@RestController
class HomeController {
	@GetMapping("/")
	public String home() {
		return "Hello, Wilmar!";
	}
}
```

# Banner

banner.txt
```text
${AnsiColor.MAGENTA}
\/\/ | |_ |\/| /\ /?   /\ |) |\/| | |\|

${AnsiColor.BLUE}:: ${application.title} ::    ${application.version}
${AnsiColor.GREEN}:: Spring Boot ::    ${spring-boot.version}
```