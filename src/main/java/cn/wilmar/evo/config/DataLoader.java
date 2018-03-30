package cn.wilmar.evo.config;

import cn.wilmar.evo.model.Role;
import cn.wilmar.evo.model.User;
import cn.wilmar.evo.repository.RoleRepository;
import cn.wilmar.evo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Component
public class DataLoader implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public static final String DEFAULT_USER = "USER";
    public static final String DEFAULT_ADMIN = "ADMIN";
    public static final String DEFAULT_USER_ROLE_NAME = "Default User";
    public static final String DEFAULT_ADMIN_ROLE_NAME = "Administrator";

    private static final String ADMIN_YINGUOWEI = "yinguowei";
    private static final String DEFAULT_PASSWORD = "111111";

    DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("DataLoader.run: args = {}", strings.toString());

        /* Role */
        roleRepository.save(new Role(DEFAULT_ADMIN, DEFAULT_ADMIN_ROLE_NAME));
        roleRepository.save(new Role(DEFAULT_USER, DEFAULT_USER_ROLE_NAME));
        roleRepository.findAll().forEach(System.out::println);

        Role defaultRole = roleRepository.findOneByKey(DEFAULT_USER).get();

        userRepository.save(new User(ADMIN_YINGUOWEI, "Yin Guo Wei", "yinguowei@gmail.com"));
        userRepository.save(new User("jojo", "Yin Xiao Qin", "yinxiaoqin2009@gmail.com"));
        userRepository.save(new User("anna", "Qian Yan", "annaleaf@qq.com"));

        userRepository.findAll().forEach(u -> {
            u.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
            u.getRoles().add(defaultRole);
            userRepository.save(u);
        });

        User admin = userRepository.findByUsername(ADMIN_YINGUOWEI);
        admin.getRoles().add(roleRepository.findOneByKey(DEFAULT_ADMIN).get());
        userRepository.save(admin);

        userRepository.findAll().forEach(System.out::println);

    }

}