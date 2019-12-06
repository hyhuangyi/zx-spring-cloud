package zx.cn.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ZxController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getAll")
    public List<String> getAllUser(){
        return  restTemplate.getForObject("http://zx-provider/getMember",List.class);
    }

    public List<String> fallback() {
        List<String>list=new ArrayList<>();
        list.add("熔断了");
        return list;
    }
}
