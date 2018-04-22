package cn.wilmar.admin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author Yin Guo Wei 2018/3/29.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WmSecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(WmSecurityConfig.class);

    @Autowired private WmUserDetailService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/**", "/example/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/img/**", "/bower_components/**", "/plugins/**", "/webjars/**").permitAll()
                .antMatchers("/", "/index.html", "/register", "/welcome_iframe.html").permitAll()
                // TODO: those manager tools's endpoint, need Toolkit Role
                .antMatchers("/admin", "/h2-console/**", "/actuator/**", "/swagger-ui.html").permitAll()
                .antMatchers("/users", "/users/**").authenticated()//.hasRole(DEFAULT_USER)
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").permitAll().successHandler(loginSuccessHandler)
                .and()
            .logout().permitAll()
                .and()
            .rememberMe()
                .tokenValiditySeconds(24 * 3600).tokenRepository(persistentTokenRepository())
                .and()
            .headers().frameOptions().disable()
                .and()
            .csrf().disable();
    }

    @Autowired LoginSuccessHandler loginSuccessHandler;

    /**
     * TODO: try other Encoder, use salt
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        logger.debug("WmSecurityConfig.bCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    // TODO why double def?
    @Qualifier("dataSource")
    @Autowired DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.eraseCredentials(true);
    }

    /*
    private static final String passwordEncoderSalt = "mYBoOT2O!7";

    @Bean
    public Md5PasswordEncoder myPasswordEncoder() {
        return new Md5PasswordEncoder() {
            @Override
            public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                return super.isPasswordValid(encPass, rawPass, passwordEncoderSalt);
            }
        };
    }*/
}
