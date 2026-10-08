package com.playzone.api.dto;
public final class AuthDtos {
  private AuthDtos(){}
  public record RegisterRequest(String email,String username,String password,String instagramUsername){}
  public record LoginRequest(String email,String password){}
  public record AuthResponse(String token, UserResponse user){}
  public record UserResponse(String id,String email,String username,String instagramUsername,String role,int xp,int wins,int losses,int tournamentWins,int runnerUps){}
}
