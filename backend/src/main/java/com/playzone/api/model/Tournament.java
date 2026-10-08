package com.playzone.api.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tournaments")
public class Tournament {
  @Id private String id;
  private String title;
  private String game;
  private String description;
  private TournamentStatus status = TournamentStatus.DRAFT;
  private int maxPlayers = 16;
  private Instant registrationClosesAt;
  private Instant startsAt;
  private boolean paidEntryEnabled = false;
  private String complianceNote = "Prize/paid-entry features are disabled until the tournament format is compliance-approved.";
  private List<String> playerIds = new ArrayList<>();
  private String winnerId;
  private String runnerUpId;
  private int winnerXp = 100;
  private int runnerUpXp = 60;
  public String getId(){return id;} public void setId(String v){id=v;}
  public String getTitle(){return title;} public void setTitle(String v){title=v;}
  public String getGame(){return game;} public void setGame(String v){game=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public TournamentStatus getStatus(){return status;} public void setStatus(TournamentStatus v){status=v;}
  public int getMaxPlayers(){return maxPlayers;} public void setMaxPlayers(int v){maxPlayers=v;}
  public Instant getRegistrationClosesAt(){return registrationClosesAt;} public void setRegistrationClosesAt(Instant v){registrationClosesAt=v;}
  public Instant getStartsAt(){return startsAt;} public void setStartsAt(Instant v){startsAt=v;}
  public boolean isPaidEntryEnabled(){return paidEntryEnabled;} public void setPaidEntryEnabled(boolean v){paidEntryEnabled=v;}
  public String getComplianceNote(){return complianceNote;} public void setComplianceNote(String v){complianceNote=v;}
  public List<String> getPlayerIds(){return playerIds;} public void setPlayerIds(List<String> v){playerIds=v;}
  public String getWinnerId(){return winnerId;} public void setWinnerId(String v){winnerId=v;}
  public String getRunnerUpId(){return runnerUpId;} public void setRunnerUpId(String v){runnerUpId=v;}
  public int getWinnerXp(){return winnerXp;} public void setWinnerXp(int v){winnerXp=v;}
  public int getRunnerUpXp(){return runnerUpXp;} public void setRunnerUpXp(int v){runnerUpXp=v;}
}
