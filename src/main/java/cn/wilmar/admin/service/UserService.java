package cn.wilmar.admin.service;

import cn.wilmar.admin.model.User;
import cn.wilmar.admin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author yinguowei 2017/7/25.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final String USERS_ALL_CACHE = "users";
    private final String USER_BY_ID_CACHE = "userById";
    private final String USER_BY_LOGIN_CACHE = "userByLogin";

    public UserService(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(USERS_ALL_CACHE)
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.debug(">>>> UserService.findAllUsers");
        List<User> users = userRepository.findAll();

        logger.debug("UserService.findAllUsers <<<<");
        return users;
    }

    @Cacheable(value = USER_BY_ID_CACHE, key = "#id")
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        System.out.println(">>>> UserService.getUserById");
        System.out.println("id = " + id);
        final User user = userRepository.findById(id).get();
        System.out.println("UserService.getUserById <<<<");
        return user;
    }

    @Cacheable(cacheNames = USER_BY_LOGIN_CACHE, key = "#username")
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        System.out.println("UserService.getUserByUsername");
        System.out.println("username = " + username);
        final User user = userRepository.findOneByUsername(username).get();
        System.out.println("UserService.getUserByUsername");
        return user;
    }

    //    @Cacheable
//    @CacheEvict(allEntries = true)
    @Caching(put = {@CachePut(key = "'id:' + #user.id"), @CachePut(key = "'username:' + #user.username")},
            evict = {@CacheEvict("users")})
    public User createUser(User user) {
        // TODO: username PK
        System.out.println(">>>> UserService.createUser");
        System.out.println("user = " + user);
        userRepository.save(user);
        System.out.println("UserService.createUser <<<<");
        return user;
    }

    @Caching(put = {
            @CachePut(cacheNames = USER_BY_ID_CACHE, key = "#user.id"),
            @CachePut(cacheNames = USER_BY_LOGIN_CACHE, key = "#user.username")},
            evict = {@CacheEvict(cacheNames = USERS_ALL_CACHE, allEntries = true)})
    public User updateUser(User user) {
        System.out.println(">>>> UserService.updateUser");
        System.out.println("user = " + user);
        userRepository.save(user);
        System.out.println("UserService.updateUser <<<<");
        return user;
    }

    public void deleteUser(long id) {
        System.out.println(">>>> UserService.deleteUser");
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            cacheManager.getCache(USER_BY_ID_CACHE).evict(user.get().getId());
            cacheManager.getCache(USER_BY_LOGIN_CACHE).evict(user.get().getUsername());
            cacheManager.getCache(USERS_ALL_CACHE).clear();
        } else {
            logger.error("Not found");
            // TODO: exception.
        }
        System.out.println("UserService.deleteUser <<<<");
    }

    public void setUserStatus(long id, boolean enable) {
        System.out.println("UserService.setUserStatus");
        final User user = getUserById(id);
        user.setActive(enable);
        updateUser(user);
        cacheManager.getCache(USER_BY_ID_CACHE).put(user.getId(), user);
        cacheManager.getCache(USER_BY_LOGIN_CACHE).put(user.getUsername(), user);
        cacheManager.getCache(USERS_ALL_CACHE).clear();
    }
}
