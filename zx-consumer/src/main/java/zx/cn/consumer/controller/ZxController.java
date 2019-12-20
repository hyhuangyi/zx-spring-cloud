package zx.cn.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import zx.cn.comm.pojo.ResultDO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZxController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getAll")
    public ResultDO getAllUser(){
        return  restTemplate.getForObject("http://zx-provider/getMember",ResultDO.class);
    }

    public ResultDO fallback() {
        return new ResultDO("0","熔断了");
    }
}
