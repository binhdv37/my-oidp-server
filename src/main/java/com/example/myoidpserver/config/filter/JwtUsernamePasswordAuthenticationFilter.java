package com.example.myoidpserver.config.filter;

import com.example.myoidpserver.config.JwtTokenUtil;
import com.example.myoidpserver.model.request.UsernamePasswordRequest;
import com.example.myoidpserver.service.CurrentUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunteco.suncloud.lib.exception.StcException;
import com.sunteco.suncloud.lib.model.UserInfo;
import com.sunteco.suncloud.lib.model.dto.shared.StcErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.authenticationManager = authenticationManager;
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UsernamePasswordRequest authenPasswordRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenPasswordRequest.getUsername(), authenPasswordRequest.getPassword());
            Authentication aAuthentication = authenticationManager.authenticate(authentication);
            return aAuthentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        if (jwtTokenUtil == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtTokenUtil = webApplicationContext.getBean(JwtTokenUtil.class);
        }

        UserInfo principal = (UserInfo) authResult.getPrincipal();

        String token = jwtTokenUtil.generateJwtToken(principal);
        String refreshToken = jwtTokenUtil.generateRefreshToken(principal.getUsername());
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(principal.getUsername());
        userInfo.setEnable(principal.getEnable());
        userInfo.setStatus(principal.getStatus());
        userInfo.setToken(token);
        userInfo.setRefreshToken(refreshToken);
        userInfo.setEmail(principal.getEmail());
        userInfo.setFullName(principal.getFullName());
        userInfo.setLastName(principal.getLastName());
        userInfo.setFirstName(principal.getFirstName());
        userInfo.setPhoneNumber(principal.getPhoneNumber());
        userInfo.setTenantId(principal.getTenantId());

        if (currentUserService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            currentUserService = webApplicationContext.getBean(CurrentUserService.class);
        }

        response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(userInfo));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed.getCause() instanceof StcException) {
            StcException exceptionx = (StcException) failed.getCause();
            StcErrorResponse errorBody = new StcErrorResponse();
            errorBody.setError(true);
            errorBody.setCode(exceptionx.getErrorCode());
            errorBody.setMessage(exceptionx.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(errorBody));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (failed instanceof DisabledException || failed instanceof LockedException) {
            StcErrorResponse errorBody = new StcErrorResponse();
            errorBody.setError(true);
            errorBody.setCode("USER_EXPIRED");
            errorBody.setMessage("user is expired");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(errorBody));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else if (failed instanceof BadCredentialsException) {
//            StcException exceptionx = (StcException) exception.getCause();
            StcErrorResponse errorBody = new StcErrorResponse();
            errorBody.setError(true);
            errorBody.setCode("INCORRECT_USERNAME_OR_PASSWORD");
            errorBody.setMessage("INCORRECT_USERNAME_OR_PASSWORD");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(errorBody));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ERROR_ON_LOGIN");
        }
//        super.unsuccessfulAuthentication(request, response, failed);
    }

}
