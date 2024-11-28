package com.reservation.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
  private static final String SECRET_KEY = "mySecretKey20241128mySecretKey20241128mySecretKey20241128"; // 환경 변수로 관리 권장
  private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public String validateToken(String token) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
      return claims.getSubject();
    } catch (JwtException | IllegalArgumentException e) {
      throw new IllegalStateException("Invalid token.");
    }
  }
}