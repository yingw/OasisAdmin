package cn.yinguowei.oasis;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import de.codecentric.boot.admin.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author yinguowei
 */
@SpringBootApplication
@EnableJpaAuditing
//@EnableAdminServer
//@EnableWebSecurity
public class OasisApplication {
    private static Logger logger = LoggerFactory.getLogger(OasisApplication.class);

    //    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
    public static void main(String[] args) {
        logger.debug("WilmarLteApplication.main");
        SpringApplication.run(OasisApplication.class, args);
    }

}

