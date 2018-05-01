
```java
// 开启缓存
@EnableCaching
```


```java

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final String USERS_ALL_CACHE = "users";
    private final String USER_BY_ID_CACHE = "userById";
    private final String USER_BY_LOGIN_CACHE = "userByLogin";

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(USERS_ALL_CACHE)
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Caching(put = {
            @CachePut(cacheNames = USER_BY_ID_CACHE, key = "#user.id"),
            @CachePut(cacheNames = USER_BY_LOGIN_CACHE, key = "#user.username")},
            evict = {@CacheEvict(cacheNames = USERS_ALL_CACHE, allEntries = true)})
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.deleteById(id);
            cacheManager.getCache(USER_BY_ID_CACHE).evict(user.getId());
            cacheManager.getCache(USER_BY_LOGIN_CACHE).evict(user.getUsername());
            cacheManager.getCache(USERS_ALL_CACHE).clear();
        });
    }

    @Cacheable(value = USER_BY_ID_CACHE, key = "#id")
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = USER_BY_LOGIN_CACHE, key = "#username")
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username).orElse(null);
    }

}

```