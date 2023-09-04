package com.example.myoidpserver.config;

import com.sunteco.suncloud.lib.Constant.MessageCode;
import com.sunteco.suncloud.lib.exception.StcException;
import com.sunteco.suncloud.lib.model.UserInfo;
import com.sunteco.suncloud.lib.model.dto.shared.StcErrorResponse;
import com.sunteco.suncloud.lib.model.dto.shared.StcSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class StcAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {


        response.sendRedirect("https://www.google.com");

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {

        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            UserInfo principal = (UserInfo) authentication.getPrincipal();
//            UserInfo user = new UserInfo();
//            user.setUsername(principal.getUsername());
//            user.setPermissions(principal.getPermissions());
//            user.setRoles(principal.getRoles());
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(principal));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        if (exception.getCause() instanceof StcException) {
            StcException stcException = (StcException) exception.getCause();
            StcErrorResponse errorBody = new StcErrorResponse();

            errorBody.setError(true);
            errorBody.setCode(stcException.getErrorCode());
            errorBody.setMessage(stcException.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(errorBody));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (exception instanceof DisabledException || exception instanceof LockedException) {
//            StcException exceptionx = (StcException) exception.getCause();
            StcErrorResponse errorBody = new StcErrorResponse();
            errorBody.setError(true);
            errorBody.setCode("USER_EXPIRED");
            errorBody.setMessage("USER_EXPIRED");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(errorBody));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            exception.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERROR_ON_LOGIN");
        }

    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        StcSuccessResponse<String> rs = new StcSuccessResponse<>();
        rs.setResult("LOGOUT_SUCCESS");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(mappingJackson2HttpMessageConverter.getObjectMapper().writeValueAsString(rs));
    }
}
