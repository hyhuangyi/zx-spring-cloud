package zx.cn.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
public class ZxController {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/getAll")
    public List<String> getAllUser(){
        return  restTemplate.getForObject("http://zx-provider/getMember",List.class);
    }
}
