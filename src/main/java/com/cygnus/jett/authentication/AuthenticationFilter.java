package com.cygnus.jett.authentication;

import com.cygnus.jett.config.AuthenticationConfig;
import com.cygnus.jett.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.info("stage=init method=doFilterInternal");

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String accessToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            accessToken = requestTokenHeader.substring(7);

            try {

                username = authenticationConfig.getUsernameFromToken(accessToken);

            } catch (IllegalArgumentException e) {
                LOGGER.error("stage=error method=doFilterInternal message=Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                LOGGER.error("stage=error method=doFilterInternal message=JWT Token has expired");
            }

        } else {
            LOGGER.warn("stage=warn method=doFilterInternal message=JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.authenticationService.loadUserByUsername(username);

            if (authenticationConfig.validateToken(accessToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }

        chain.doFilter(request, response);

        LOGGER.info("stage=end method=doFilterInternal");
    }

}
