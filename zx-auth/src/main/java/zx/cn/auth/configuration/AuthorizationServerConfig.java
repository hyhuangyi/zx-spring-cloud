package zx.cn.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import zx.cn.auth.service.MyUserDetailsService;
import zx.cn.comm.consts.RedisConst;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 配置授权服务器端点
     * @param endpoints
     * @throws Exception
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)//若无，refresh_token会有UserDetailsService is required错误
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()//允许表单认证
                .tokenKeyAccess("permitAll()")// 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("isAuthenticated()"); // 开启/oauth/check_token验证端口认证权限访问
    }

    /**
     * 配置客户端详情信息，客户端详情信息在这里进行初始化
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("webapp")
                .secret("webapp")
                .scopes("webapp")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
                .redirectUris("http://baidu.com");
    }

    /**
     * 注意，自定义TokenServices的时候，需要设置@Primary，否则报错
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        // 这里如果设置为false则不能更新refresh_token，如果需要刷新token的功能需要设置成true
        tokenServices.setSupportRefreshToken(true);
        // 设置上次RefreshToken是否还可以使用 默认为true
        tokenServices.setReuseRefreshToken(false);
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
        // 默认30天，这里修改
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenServices;
    }

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey(RedisConst.JWT_SIGNING_KEY);
        return accessTokenConverter;
    }

    /**
     * token store 存儲token
     * @return
     */
    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }
}
