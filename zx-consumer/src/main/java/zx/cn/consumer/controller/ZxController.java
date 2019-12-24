package zx.cn.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import zx.cn.comm.pojo.ResultDO;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ZxController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('getAll')")
    public ResultDO getAllUser(){
        return  restTemplate.getForObject("http://zx-provider/getMember",ResultDO.class);
    }

    public ResultDO fallback() {
        return new ResultDO("0","熔断了");
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyAuthority('user')")
    public Principal user(Principal principal) {
        return principal;
    }
}
