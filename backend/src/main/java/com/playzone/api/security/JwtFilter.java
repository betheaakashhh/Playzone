package com.playzone.api.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.playzone.api.model.User;
import com.playzone.api.repository.UserRepository;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtService jwt; private final UserRepository users;
  public JwtFilter(JwtService jwt,UserRepository users){this.jwt=jwt;this.users=users;}
  @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
    String h=req.getHeader("Authorization");
    if(h!=null&&h.startsWith("Bearer ")) try{
      User u=users.findById(jwt.userId(h.substring(7))).orElse(null);
      if(u!=null) SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.getId(),null,java.util.List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole()))));
    }catch(Exception ignored){}
    chain.doFilter(req,res);
  }
}
