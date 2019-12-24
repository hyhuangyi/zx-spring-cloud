package zx.cn.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zx.cn.auth.domain.MyUserDetails;
import zx.cn.auth.domain.Token;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Token token=new Token();
        MyUserDetails userDetails=new MyUserDetails(token);
        return userDetails;
    }
}
