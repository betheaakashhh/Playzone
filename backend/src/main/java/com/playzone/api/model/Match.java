package com.playzone.api.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("matches")
public class Match {
  @Id private String id;
  private String tournamentId;
  private String player1Id;
  private String player2Id;
  private String winnerId;
  private String evidenceUrl;
  private MatchStatus status = MatchStatus.SCHEDULED;
  private String reportedBy;
  private String disputeReason;
  private Instant scheduledAt;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTournamentId(){return tournamentId;} public void setTournamentId(String v){tournamentId=v;}
  public String getPlayer1Id(){return player1Id;} public void setPlayer1Id(String v){player1Id=v;}
  public String getPlayer2Id(){return player2Id;} public void setPlayer2Id(String v){player2Id=v;}
  public String getWinnerId(){return winnerId;} public void setWinnerId(String v){winnerId=v;}
  public String getEvidenceUrl(){return evidenceUrl;} public void setEvidenceUrl(String v){evidenceUrl=v;}
  public MatchStatus getStatus(){return status;} public void setStatus(MatchStatus v){status=v;}
  public String getReportedBy(){return reportedBy;} public void setReportedBy(String v){reportedBy=v;}
  public String getDisputeReason(){return disputeReason;} public void setDisputeReason(String v){disputeReason=v;}
  public Instant getScheduledAt(){return scheduledAt;} public void setScheduledAt(Instant v){scheduledAt=v;}
}
