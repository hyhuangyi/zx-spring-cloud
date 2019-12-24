package zx.cn.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages={"zx.cn"})
public class ZxProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxProviderApplication.class, args);
    }

}
