package com.playzone.api.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final SecretKey key; private final long expiration;
  public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
    byte[] raw=secret.getBytes(StandardCharsets.UTF_8); if(raw.length<32) throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");
    key=Keys.hmacShaKeyFor(raw); this.expiration=expiration;
  }
  public String generate(String userId,String email,String role){return Jwts.builder().subject(userId).claim("email",email).claim("role",role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(key).compact();}
  public String userId(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
