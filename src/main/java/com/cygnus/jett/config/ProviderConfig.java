package com.cygnus.jett.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ProviderConfig {

    @Value("${authentication.secret}")
    private String secret;

    @Value("${authentication.username}")
    private String username;

    @Value("${authentication.password}")
    private String password;

    @Value("${authentication.shutdown}")
    private boolean authenticationDisabled;

}
