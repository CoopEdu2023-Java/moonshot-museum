package cn.msa.msa_museum_server.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import cn.msa.msa_museum_server.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String SECRET_KEY = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String setToken(User userEntity) {
        return Jwts
                .builder()
                .setSubject(userEntity.getId().toString())
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .compact();
    }

    public String decodeJwt(String token) throws JwtException {
        return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}