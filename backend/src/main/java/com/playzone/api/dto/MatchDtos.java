package com.playzone.api.dto;
public final class MatchDtos {
  private MatchDtos(){}
  public record ResultRequest(String winnerId,String evidenceUrl){}
  public record DisputeRequest(String reason){}
}
