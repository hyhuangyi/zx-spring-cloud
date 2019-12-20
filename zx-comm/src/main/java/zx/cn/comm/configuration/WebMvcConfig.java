package zx.cn.comm.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import zx.cn.comm.consts.RedisConst;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by huangYi on 2018/8/18
 * 替换spring boot默认的mvc配置
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    /*跨域问题 springboot*/
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader(RedisConst.AUTHORIZATION);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(1);
        return bean;
    }

    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 设置spring mvc的解析器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<?> jsonConverter = new MappingJackson2HttpMessageConverter();
        HttpMessageConverter<?> stringConverter = new StringHttpMessageConverter(CHARSET);
        converters.add(jsonConverter);
        converters.add(stringConverter);
    }
}
