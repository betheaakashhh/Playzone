package com.playzone.api.dto;
import java.time.Instant;
import com.playzone.api.model.TournamentStatus;
public final class TournamentDtos {
  private TournamentDtos(){}
  public record CreateRequest(String title,String game,String description,Integer maxPlayers,Instant registrationClosesAt,Instant startsAt){}
  public record StatusRequest(TournamentStatus status){}
  public record WinnerRequest(String winnerId,String runnerUpId){}
}
