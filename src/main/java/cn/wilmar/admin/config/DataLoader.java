package cn.wilmar.admin.config;

import cn.wilmar.admin.example.Example;
import cn.wilmar.admin.example.ExampleRepository;
import cn.wilmar.admin.model.Resource;
import cn.wilmar.admin.model.Role;
import cn.wilmar.admin.model.User;
import cn.wilmar.admin.repository.RoleRepository;
import cn.wilmar.admin.repository.UserRepository;
import cn.wilmar.admin.model.RoleType;
import cn.wilmar.admin.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Component
public class DataLoader implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    public static final String DEFAULT_USER = "USER";
    public static final String DEFAULT_ADMIN = "ADMIN";
    public static final String DEFAULT_USER_ROLE_NAME = "默认用户";
    public static final String DEFAULT_ADMIN_ROLE_NAME = "管理员";
    private static final Random RANDOM = new Random();
    private static final String ADMIN_YINGUOWEI = "yinguowei";
    private static final String DEFAULT_PASSWORD = "111111";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ExampleRepository exampleRepository;
    private final ResourceRepository resourceRepository;

    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    DataLoader(UserRepository userRepository, RoleRepository roleRepository, ExampleRepository exampleRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.exampleRepository = exampleRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("DataLoader.run: args = {}", strings.toString());

        /* Role */
        roleRepository.save(new Role(DEFAULT_ADMIN, DEFAULT_ADMIN_ROLE_NAME, RoleType.SYSTEM));
        roleRepository.save(new Role(DEFAULT_USER, DEFAULT_USER_ROLE_NAME, RoleType.SYSTEM));
        roleRepository.save(new Role("ACCOUNT_ADMIN", "账号管理员", RoleType.CUSTOM));
        roleRepository.save(new Role("DEPT_ADMIN", "部门管理员", RoleType.CUSTOM));

        roleRepository.findAll().forEach(System.out::println);

        Role defaultRole = roleRepository.findOneByKey(DEFAULT_USER).get();

        /* User */
        userRepository.save(new User(ADMIN_YINGUOWEI, "Yin Guo Wei", "yinguowei@gmail.com"));
        userRepository.save(new User("jojo", "Yin Xiao Qin", "yinxiaoqin2009@gmail.com"));
        userRepository.save(new User("anna", "Qian Yan", "annaleaf@qq.com"));

        userRepository.findAll().forEach(u -> {
            u.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
            u.getRoles().add(defaultRole);
            userRepository.save(u);
        });

        User admin = userRepository.findOneByUsername(ADMIN_YINGUOWEI).get(); // TODO: use service.
        admin.getRoles().add(roleRepository.findOneByKey(DEFAULT_ADMIN).get());
        userRepository.save(admin);

        userRepository.findAll().forEach(System.out::println);

        /* Example */
        for (int i = 0; i < 30; i++) {
            Example example = new Example("name" + i, "name" + i + "@example.com");
            example.setDateField(LocalDate.now());
            example.setDateTimeField(LocalDateTime.now());
            example.setEnable(RANDOM.nextBoolean());
            example.setFloatField(RANDOM.nextFloat() * 100.f);
            example.setNumberField(RANDOM.nextInt(100));
            example.setParent(null);
            exampleRepository.save(example);
        }

        /* Resource */
        Resource security = new Resource("权限中心", "security", 1, 1);
        resourceRepository.save(security);
        resourceRepository.save(new Resource("用户管理", "security:user", 1, 2).setParent(security));
        resourceRepository.save(new Resource("角色管理", "security:role", 2, 2).setParent(security));
        resourceRepository.save(new Resource("资源管理", "security:resource", 3, 2).setParent(security));

        final Resource settings = new Resource("系统设置", "settings", 2, 1);
        resourceRepository.save(settings);
        resourceRepository.save(new Resource("基础设置", "settings:basis", 1, 2).setParent(settings));
        resourceRepository.save(new Resource("用户Session", "settings:token", 2, 2).setParent(settings));
        resourceRepository.save(new Resource("登入日志", "settings:log", 3, 2).setParent(settings));
        resourceRepository.save(new Resource("统计", "settings:statistics", 4, 2).setParent(settings));

        /* 授权 */
        Role adminRole = roleRepository.findOneByKey(DEFAULT_ADMIN).get();
        resourceRepository.findAll().forEach(resource -> adminRole.getResources().add(resource));
        roleRepository.save(adminRole);
        Role accountRole = roleRepository.findOneByKey("ACCOUNT_ADMIN").get();
        accountRole.getResources().add(resourceRepository.findOneByResourceKey("security:user").get());
        roleRepository.save(accountRole);

    }

}