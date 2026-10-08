package com.playzone.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.playzone.api.dto.AuthDtos.*;
import com.playzone.api.model.User;
import com.playzone.api.repository.UserRepository;
import com.playzone.api.security.JwtService;

@Service public class AuthService {
  private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
  public AuthService(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
  public AuthResponse register(RegisterRequest r){
    if(users.findByEmail(r.email()).isPresent()) throw new IllegalArgumentException("Email already registered");
    if(users.findByUsername(r.username()).isPresent()) throw new IllegalArgumentException("Username already taken");
    User u=new User(); u.setEmail(r.email().trim().toLowerCase()); u.setUsername(r.username().trim()); u.setPasswordHash(encoder.encode(r.password())); u.setInstagramUsername(r.instagramUsername()); users.save(u); return response(u);
  }
  public AuthResponse login(LoginRequest r){User u=users.findByEmail(r.email().trim().toLowerCase()).orElseThrow(()->new IllegalArgumentException("Invalid credentials")); if(!encoder.matches(r.password(),u.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials"); return response(u);}
  public UserResponse publicUser(User u){return new UserResponse(u.getId(),u.getEmail(),u.getUsername(),u.getInstagramUsername(),u.getRole().name(),u.getXp(),u.getWins(),u.getLosses(),u.getTournamentWins(),u.getRunnerUps());}
  private AuthResponse response(User u){return new AuthResponse(jwt.generate(u.getId(),u.getEmail(),u.getRole().name()),publicUser(u));}
}
