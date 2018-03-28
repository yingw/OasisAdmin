package cn.wilmar.lte;

import cn.wilmar.lte.model.Role;
import cn.wilmar.lte.model.User;
import cn.wilmar.lte.repository.RoleRepository;
import cn.wilmar.lte.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;


/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Component
public class DataLoader implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("DataLoader.run: args = {}", strings.toString());

        /* Role */
        Stream.of("Admin", "User").forEach(name -> roleRepository.save(new Role(name.toLowerCase(), name)));
        roleRepository.findAll().forEach(System.out::println);

        Role defaultRole = roleRepository.findOneByKey("user").get();

        userRepository.save(new User("yinguowei", "Yin Guo Wei", "yinguowei@gmail.com"));
        userRepository.save(new User("jojo", "Yin Xiao Qin", "yinxiaoqin2009@gmail.com"));
        userRepository.save(new User("anna", "Qian Yan", "annaleaf@qq.com"));

        userRepository.findAll().forEach(u -> {
            u.setPassword("d8d8a6cd1ed4902505882b6c901812b2");
            u.getRoles().add(defaultRole);
            userRepository.save(u);
        });

        User yinguowei = userRepository.findByUsername("yinguowei");
        yinguowei.getRoles().add(roleRepository.findOneByKey("admin").get());
        userRepository.save(yinguowei);

        userRepository.findAll().forEach(System.out::println);

    }

}