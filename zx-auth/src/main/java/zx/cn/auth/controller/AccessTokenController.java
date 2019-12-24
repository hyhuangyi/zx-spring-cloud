package zx.cn.auth.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import zx.cn.auth.domain.AccessDto;
import zx.cn.comm.pojo.ResultDO;

@RestController
public class AccessTokenController {
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation("获取accessToken")
    @PostMapping("/login")
    public ResultDO login(@ModelAttribute AccessDto dto){
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("grant_type",dto.getGrant_type());
        paramMap.add("username",dto.getUsername());
        paramMap.add("password",dto.getPassword());
        paramMap.add("client_id",dto.getClient_id());
        paramMap.add("client_secret",dto.getClient_secret());
        return restTemplate.postForObject("http://zx-auth/oauth/token",paramMap,ResultDO.class);
    }

    @ApiOperation("刷新accessToken")
    @PostMapping("/refresh")
    public ResultDO refresh(String refresh_token,String client_id,String client_secret){
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("grant_type","refresh_token");
        paramMap.add("refresh_token",refresh_token);
        paramMap.add("client_id",client_id);
        paramMap.add("client_secret",client_secret);
        return restTemplate.postForObject("http://zx-auth/oauth/token",paramMap,ResultDO.class);
    }
}
