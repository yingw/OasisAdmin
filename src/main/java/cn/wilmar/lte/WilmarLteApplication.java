package cn.wilmar.lte;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableJpaAuditing
public class WilmarLteApplication {
    private static Logger logger = LoggerFactory.getLogger(WilmarLteApplication.class);

    //    @Bean
//    public LayoutDialect layoutDialect() {
//        return new LayoutDialect();
//    }
    public static void main(String[] args) {
        logger.debug("WilmarLteApplication.main");
        SpringApplication.run(WilmarLteApplication.class, args);
    }
}