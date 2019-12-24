package zx.cn.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProviderController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String serviceName;

    @ApiOperation("provider 测试")
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
