package com.playzone.api.model;

import java.time.Instant;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {
  @Id private String id;
  @Indexed(unique = true) private String email;
  @Indexed(unique = true) private String username;
  private String passwordHash;
  private String instagramUsername;
  private String avatarUrl;
  private Role role = Role.PLAYER;
  private int xp;
  private int wins;
  private int losses;
  private int tournamentWins;
  private int runnerUps;
  private Instant createdAt = Instant.now();
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
  public String getInstagramUsername(){return instagramUsername;} public void setInstagramUsername(String v){instagramUsername=v;}
  public String getAvatarUrl(){return avatarUrl;} public void setAvatarUrl(String v){avatarUrl=v;}
  public Role getRole(){return role;} public void setRole(Role v){role=v;}
  public int getXp(){return xp;} public void setXp(int v){xp=v;}
  public int getWins(){return wins;} public void setWins(int v){wins=v;}
  public int getLosses(){return losses;} public void setLosses(int v){losses=v;}
  public int getTournamentWins(){return tournamentWins;} public void setTournamentWins(int v){tournamentWins=v;}
  public int getRunnerUps(){return runnerUps;} public void setRunnerUps(int v){runnerUps=v;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
