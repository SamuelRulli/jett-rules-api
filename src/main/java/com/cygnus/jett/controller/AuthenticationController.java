package com.cygnus.jett.controller;

import com.cygnus.jett.config.AuthenticationConfig;
import com.cygnus.jett.controller.request.AuthenticationRequest;
import com.cygnus.jett.controller.response.AuthenticationResponse;
import com.cygnus.jett.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*", methods = {POST})
public class AuthenticationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final AuthenticationConfig authenticationConfig;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationConfig authenticationConfig, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationConfig = authenticationConfig;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAccessToken(@RequestBody AuthenticationRequest requestBody) throws Exception {
        LOGGER.info("stage=init method=createAccessToken");

        authenticate(requestBody.getUsername(), requestBody.getPassword());

        final UserDetails userDetails = authenticationService.loadUserByUsername(requestBody.getUsername());

        final String token = authenticationConfig.generateToken(userDetails);

        LOGGER.info("stage=end method=createAccessToken");

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        LOGGER.info("stage=init method=authenticate");
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            LOGGER.error("stage=error method=authenticate message={} USER_DISABLED" + e);
            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {
            LOGGER.error("stage=error method=authenticate message={} INVALID_CREDENTIALS" + e);
            throw new Exception("INVALID_CREDENTIALS", e);

        }
        LOGGER.info("stage=end method=authenticate");
    }

}
