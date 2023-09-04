package com.example.myoidpserver.config;

import com.example.myoidpserver.config.filter.JwtTokenVerifyFilter;
import com.example.myoidpserver.config.filter.JwtUsernamePasswordAuthenticationFilter;
import com.example.myoidpserver.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity()
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final List<String> PERMIT_ALL_URLS = Arrays.asList("/login", "/v1/user/refresh-token");

    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl userDetailService;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    StcAuthenticationHandler authenticationHandler;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserDetailServiceImpl userDetailService, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtTokenVerifyFilter jwtTokenVerifyFilter = new JwtTokenVerifyFilter(authenticationManager());
        JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter = new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), mappingJackson2HttpMessageConverter);
        jwtUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationHandler);
        jwtUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationHandler);

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtTokenVerifyFilter)
                .addFilterAfter(jwtUsernamePasswordAuthenticationFilter, JwtTokenVerifyFilter.class)
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_URLS.toArray(new String[PERMIT_ALL_URLS.size()]))
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
