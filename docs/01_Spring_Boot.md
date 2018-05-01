# Spring Boot

## 新建项目

- Setup up this project using IDES's new project guide, or
- Go to the [Spring Initializr](http://start.spring.io/) and create project.

Select Spring Boot version 2.0.1.RELEASE (2018-4-12)

And choose these dependencies
 ```
 - DevTools
 - Lombok
 - Rest Repositories （建议后期去掉 rest）
 - JPA
 - H2
```

要使用 Lombok，还需要设置 Eclipse、IntelliJ 插件

Run with: `mvn spring-boot:run`

Visit [http://localhost:8080](http://localhost:8080)

## 创建第一个简单模型 User，Create a simple domain module to test

### Gender 枚举
创建枚举对象，性别，以及随机函数（用于测试）
```java
public enum Gender {
    MALE, FEMALE;

    private static final List<Gender> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Gender randomGender() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
```

### User 对象
属性


字段 | 名称 | 类型 | 其他
---|---|---|---
id | ID | Long | 主键
username | 账户名 | String | 非空、唯一
fullname | 用户姓名 | String | 非空
password | 密码 | String | JsonIgnore
email | 邮箱 | String | 非空、唯一
gender | 性别 | 枚举 | 随机（测试）
enable | 状态 | String | 随机（测试）


最终版本
```java
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull @NotEmpty @Column(unique = true)
    String username;

    @NonNull @NotEmpty
    String fullname;

    @JsonIgnore
    String password;

    @NonNull @NotEmpty @Column(unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender = Gender.randomGender();
}
```
## 数据访问对象（DAO/Repository)

User Repo
```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```
## 测试数据 DataLoader
```java
@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private static final String[] TEST_USER_NAMES = {"Anna", "Jojo", "Gary"};
    private final UserRepository userRepository;

    DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("DataLoader.run");
        /* User */
        Stream.of(TEST_USER_NAMES).forEach(name -> userRepository.save(new User(name.toLowerCase(), name, name.toLowerCase() + "@example.com")));
        userRepository.findAll().forEach(System.out::println);
    }

}
```
## 设置属性
application.properties:
### 数据库
```properties

# H2 数据库的配置

spring.jpa.database=h2
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```



## 其他属性

```properties
# log level
logging.level.cn.yinguowei.jarvis=debug
# 启用 gzip 压缩
server.compression.enabled=true
```


## 启动测试
http://localhost:8080/
http://localhost:8080/users
查看h2数据库

http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
sa 密码空

# 其他


> About open-is-view, read [This article](https://github.com/spring-projects/spring-boot/issues/7107).

Finally, rebuild (for you choose DevTools) to hot deploy, or rerun to have a test at: http://localhost:8080/api/users

Or use curl to test:
```shell

```

```properties

# 修改默认的 RestRepositoryResource 路径
spring.data.rest.base-path=/api
```
## TODO

dependencies:
```
 - Security
 - Cache
 - Web
 - MySQL
 - Actuator
```

Change to yml?



异步请求超时时间
```properties

# 设定async请求的超时时间，以毫秒为单位，如果没有设置的话，以具体实现的超时时间为准，比如tomcat的servlet3的话是10秒.
spring.mvc.async.request-timeout=-1
```