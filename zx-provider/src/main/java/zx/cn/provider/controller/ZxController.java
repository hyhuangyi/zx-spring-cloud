package zx.cn.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ZxController {

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

    @ApiOperation("用户信息")
    @GetMapping("/current")
    @PreAuthorize("hasAnyAuthority('user')")
    public Principal user(Principal principal) {
        return principal;
    }

    @ApiOperation("测试权限不足")
    @GetMapping("/demo")
    @PreAuthorize("hasAnyAuthority('demo')")
    public int test() {
        return 1;
    }
}
