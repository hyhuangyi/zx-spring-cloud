package zx.cn.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zx.cn.comm.pojo.ResultDO;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/member")
    public Principal user(Principal member) {
        return member;
    }

    @DeleteMapping(value = "/exit")
    public ResultDO revokeToken(String access_token) {
        ResultDO result = new ResultDO();
        if (consumerTokenServices.revokeToken(access_token)) {
            result.setCode("200");
            result.setMsg("注销成功");
        } else {
            result.setCode("0");
            result.setMsg("注销失败");
        }
        return result;
    }
}