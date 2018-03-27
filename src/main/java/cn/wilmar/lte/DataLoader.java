package cn.wilmar.lte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Component
public class DataLoader implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserRepository repository;

    DataLoader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("DataLoader.run: args = {}", strings.toString());
        repository.save(new User("yinguowei", "Yin Guo Wei"));
        repository.save(new User("jojo", "Yin Xiao Qin"));
        repository.save(new User("anna", "Qian Yan"));

        repository.findAll().forEach(System.out::println);
    }

}