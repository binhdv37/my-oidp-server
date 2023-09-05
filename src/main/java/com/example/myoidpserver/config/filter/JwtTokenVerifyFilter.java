package com.example.myoidpserver.config.filter;

import com.example.myoidpserver.config.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunteco.suncloud.lib.model.UserInfo;
import com.sunteco.suncloud.lib.model.dto.shared.StcErrorResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifyFilter extends BasicAuthenticationFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtTokenVerifyFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getRequestURI().contains("/user/refresh-token")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (jwtTokenUtil == null) {
            ServletContext servletContext = httpServletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtTokenUtil = webApplicationContext.getBean(JwtTokenUtil.class);
        }
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            Jws<Claims> claimJwts = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtTokenUtil.getSecret_key().getBytes())).parseClaimsJws(token);

            claimJwts.getHeader();
            Claims body = claimJwts.getBody();
            String username = body.getSubject();
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);

            userInfo.setToken(token);
            if (body.get("id") != null) userInfo.setId(Long.valueOf(body.get("id").toString()));
            if (body.get("fullName") != null) userInfo.setFullName((String) body.get("fullName"));
            if (body.get("email") != null) userInfo.setEmail((String) body.get("email"));
            if (body.get("lastName") != null) userInfo.setLastName((String) body.get("lastName"));
            if (body.get("firstName") != null) userInfo.setFirstName((String) body.get("firstName"));
            if (body.get("phoneNumber") != null) userInfo.setPhoneNumber((String) body.get("phoneNumber"));
            if (body.get("status") != null) userInfo.setStatus(Integer.valueOf(body.get("status").toString()));
            if (body.get("tenantId") != null) userInfo.setTenantId((String) body.get("tenantId"));
            if (body.get("accountType") != null) userInfo.setAccountType((Integer) body.get("accountType"));


            Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (JwtException e) {
            if (e instanceof ExpiredJwtException) {
                logger.info(String.format("token %s cannot is expired .", token));
                StcErrorResponse errorBody = new StcErrorResponse();
                errorBody.setError(true);
                errorBody.setCode("TOKEN_EXPIRED");
                errorBody.setMessage("Token is expired");

                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

                mapper.writeValue(httpServletResponse.getWriter(), errorBody);
            } else {
                throw new IllegalStateException(String.format("token %s cannot be trust ;", token));
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
