package zx.cn.auth.domain;

import lombok.Data;

@Data
public class AccessDto {
    private String grant_type;
    private String username;
    private String password;
    private String client_id;
    private String client_secret;
}
