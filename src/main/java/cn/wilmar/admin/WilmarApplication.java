package cn.wilmar.admin;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import de.codecentric.boot.admin.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author yinguowei
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
//@EnableAdminServer
//@EnableWebSecurity
public class WilmarApplication {
    private static Logger logger = LoggerFactory.getLogger(WilmarApplication.class);

    //    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
    public static void main(String[] args) {
        logger.debug("WilmarLteApplication.main");
        SpringApplication.run(WilmarApplication.class, args);
    }

//    @Bean
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
