package com.jj.vreden.filter;

import com.jj.vreden.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(JWTUtil.AUTHORIZATION_HEADER);

        if(authorizationHeader == null || !authorizationHeader.startsWith(JWTUtil.AUTHORIZATION_HEADER_CONTENT_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Jws<Claims> jws = JWTUtil.tryParseJwtToken(authorizationHeader.substring(JWTUtil.AUTHORIZATION_HEADER_CONTENT_PREFIX.length() + 1));
            String username = jws.getBody().getSubject();

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        } catch (Exception e) {
        }

        chain.doFilter(request, response);
    }
}
