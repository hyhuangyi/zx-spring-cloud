package zx.cn.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

public class MyUserDetails implements UserDetails {
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private String password;
    private Token token;

    public MyUserDetails(Token token) {
        this.token = token;
        //插入角色权限 角色必须是ROLE_开头
        for(String role:token.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        //TODO 还可以更细粒度权限
        //默认加几个测试
        authorities.add(new SimpleGrantedAuthority("user"));
        authorities.add(new SimpleGrantedAuthority("getAll"));
        authorities.add(new SimpleGrantedAuthority("test"));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.token.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
