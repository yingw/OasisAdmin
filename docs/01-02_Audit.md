
### 开启JPA审计功能

`@EnableJpaAuditing`

### AbstractAuditingEntity

**创建 Audit 父类 AbstractAuditingEntity**
4个属性用于 Audit

字段 | 类型 | 其他 | 初始值
---|---|---|---
createdBy | String 50 | 非空，不可更新
createdDate | LocalDateTime | 非空，不可更新 | LocalDateTime.now()
lastModifiedBy | String 50 | 非空
lastModifiedDate | LocalDateTime | 非空 | LocalDateTime.now()


注解 @MappedSuperclass 声明为不要映射数据库对象

注解 `@EntityListeners(AuditingEntityListener.class)` 声明 Audit

注解
- @CreatedBy
- @CreatedDate
- @LastModifiedBy
- @LastModifiedDate

最终版本
```java
@Data
@MappedSuperclass
public abstract class AbstractAuditingEntity {

    @CreatedBy
    @Column(nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

}

```

### 默认的 AuditorAware
用来创建 cratedBy lastModifiedBy
```java
@Component
public class DefaultAuditorAware implements AuditorAware<String> {
    private static final Optional<String> SYSTEM_ACCOUNT = Optional.of("SYSTEM");

    @Override
    public Optional<String> getCurrentAuditor() {
            return SYSTEM_ACCOUNT;
    }
}

```
注解 `@EqualsAndHashCode(callSuper = false)` 声明不调用父类的 Hash 和 Equals

User 对象集成，并增加2个注解
```java
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
public class User extends AbstractAuditingEntity {

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