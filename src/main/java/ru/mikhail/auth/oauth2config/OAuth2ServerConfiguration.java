package ru.mikhail.auth.oauth2config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.KeyPair;

@Import(AuthorizationServerEndpointsConfiguration.class)
@Configuration
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {
    final
    AuthenticationManager authenticationManager;
    final
    KeyPair keyPair;
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    final
    UserDetailsService userDetailsService;

    public OAuth2ServerConfiguration(AuthenticationConfiguration authenticationConfiguration, KeyPair keyPair, UserDetailsService userDetailsService) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.keyPair = keyPair;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource)
//        .passwordEncoder(passwordEncoder);
        clients.inMemory()
                .withClient("adapter")
                .secret("$2y$10$tTm3tG0rbA6POtTdA0dSn.iz8dThfCWxCsVGOyUkGmaMj3nbSPhqq")
                .scopes("user_info")
                .autoApprove(true)
                .authorizedGrantTypes("password", "client_credentials", "refresh_token");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(this.keyPair);
        return converter;
    }
    @Bean
    TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
}
