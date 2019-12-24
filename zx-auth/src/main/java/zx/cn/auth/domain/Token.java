package zx.cn.auth.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class Token implements Serializable {
    private String uuid;
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private String token;
    private String[] roles = new String[0];
}
