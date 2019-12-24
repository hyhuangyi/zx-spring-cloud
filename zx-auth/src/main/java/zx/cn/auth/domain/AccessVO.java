package zx.cn.auth.domain;

import lombok.Data;

@Data
public class AccessVO {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
}
