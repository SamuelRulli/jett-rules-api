package com.cygnus.jett.service;

import com.cygnus.jett.config.ProviderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ProviderConfig providerConfig;

    public AuthenticationService(ProviderConfig providerConfig) {
        this.providerConfig = providerConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("stage=init method=loadUserByUsername");

        if (username.equals(providerConfig.getUsername()) ) {
            LOGGER.info("stage=end method=loadUserByUsername");
            return new User(username, passwordEncoder.encode(providerConfig.getPassword()), new ArrayList<>());
        } else {
            LOGGER.info("stage=end method=loadUserByUsername message=User not found - {} " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

}
