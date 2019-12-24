package zx.cn.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ZxController {

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
