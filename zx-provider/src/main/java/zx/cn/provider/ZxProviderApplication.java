package zx.cn.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"zx.cn"})
public class ZxProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxProviderApplication.class, args);
    }

}
