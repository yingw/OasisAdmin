package cn.yinguowei.oasis.config;

//import cn.yinguowei.boot.configration.MyErrorInterceptor;
import cn.yinguowei.oasis.security.WmErrorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yinguowei 2017/7/19.
 */
@Configuration
public class WmWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WmErrorInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
