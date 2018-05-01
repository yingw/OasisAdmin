可以去掉 Rest

也可以保留 Rest，设置 path
```properties
spring.data.rest.base-path=/api
```


## Service
```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

```

## Controller
可以返回对象，也可以用 ResponseEntity 返回对象加 HTTP 状态
```java
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
}

```
http://localhost:8080/users

## 完整 CRUD

Controller 未实现
```java

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return new ResponseEntity<>(new User() , HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
```
测试

```
$ curl -X GET "http://localhost:8080/users"
$ curl -X GET "http://localhost:8080/users/1"
curl http://localhost:8080/users/2 -X DELETE
# curl http://localhost:8080/users -X POST -H "Content-Type:application/json" -H "Accept:application/json" -d '{"username":"yinguowei", "fullname":"Yin Guo Wei", "email":"yinguwoei@gmail.com"}'
curl http://localhost:8080/users -X POST -H "Content-Type:application/json" -d '{"username":"anna", "fullname":"Yin Guo Wei", "email":"yinguwoei@gmail.com"}'
curl http://localhost:8080/users/1 -X PUT -H "Content-Type:application/json" -d '{"username":"yinguowei", "fullname":"Yin Guo Wei", "email":"yinguwoei@gmail.com"}'

```

实现

Service
```java

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username).orElse(null);
    }

}

```

Controller

```java

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        User user0 = userService.getUserByUsername(user.getUsername());
        if (user0 != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.OK);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User user0 = userService.getUser(id);
        if (user0 == null ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user0.setEmail(user.getEmail());
        user0.setFullname(user.getFullname());
        user0.setGender(user.getGender());
        user0.setUsername(user.getUsername());
        return new ResponseEntity<>(userService.saveOrUpdateUser(user0), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}


```