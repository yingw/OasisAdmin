package cn.wilmar.evo;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import de.codecentric.boot.admin.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaAuditing
//@EnableAdminServer
//@EnableWebSecurity
public class WilmarEVOApplication {
    private static Logger logger = LoggerFactory.getLogger(WilmarEVOApplication.class);

    //    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
    public static void main(String[] args) {
        logger.debug("WilmarLteApplication.main");
        SpringApplication.run(WilmarEVOApplication.class, args);
    }

}

