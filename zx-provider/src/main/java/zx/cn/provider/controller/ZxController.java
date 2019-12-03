package zx.cn.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ZxController {

    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String serviceName;

    @GetMapping("/getMember")
    public List<String> getMember(){
        List<String> list = new ArrayList<>();
        list.add("xm");
        list.add("xh");
        list.add("xz");
        list.add("xl");
        list.add(serviceName+":"+port);
        return list;
    }
}
