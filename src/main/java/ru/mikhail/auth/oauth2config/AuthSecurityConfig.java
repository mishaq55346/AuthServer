package ru.mikhail.auth.oauth2config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@Order(2)
public class AuthSecurityConfig extends AuthorizationServerSecurityConfiguration {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
            //.mvcMatchers("/token").authenticated()
                .requestMatchers()
                .mvcMatchers("/oauth2/**","/**", "/.well-known/jwks.json")
                .and()
                .authorizeRequests()
                .mvcMatchers("/oauth2/**","/**", "/.well-known/jwks.json").permitAll();
    }
}
