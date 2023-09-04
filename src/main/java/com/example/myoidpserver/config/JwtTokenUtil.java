package com.example.myoidpserver.config;

import com.sunteco.suncloud.lib.model.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Date;

@Configuration
public class JwtTokenUtil {

    @Value("${stc.jwt.secret.key}")
    private String secret_key;

    @Value("${stc.jwt.refresh.secret.key}")
    private String refresh_secret_key;

    public String generateJwtToken(UserInfo principal) {
        String token = Jwts.builder().setSubject(principal.getUsername())
                .claim("username", principal.getUsername())
                .claim("id", principal.getId())
                .claim("email", principal.getEmail())
                .claim("fullName", principal.getFullName())
                .claim("status", principal.getStatus())
                .claim("tenantId", principal.getTenantId())
                .claim("accountType", principal.getAccountType())
                .setIssuedAt(new Date())
                // token expired after 0.5 day
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(12).toInstant()))
//            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(2).toInstant()))
                .signWith(Keys.hmacShaKeyFor(secret_key.getBytes()))
                .compact();
        return token;
    }

    public String generateRefreshToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(7 * 24).toInstant()))
//            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
                .signWith(Keys.hmacShaKeyFor(refresh_secret_key.getBytes()))
                .compact();
        return token;
    }

    public String getSecret_key() {
        return this.secret_key;
    }

    public String getRefresh_secret_key() {
        return this.refresh_secret_key;
    }

}
