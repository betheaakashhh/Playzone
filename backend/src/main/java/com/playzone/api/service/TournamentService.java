package com.playzone.api.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.playzone.api.dto.TournamentDtos.*;
import com.playzone.api.model.*;
import com.playzone.api.repository.*;

@Service public class TournamentService {
  private final TournamentRepository tournaments; private final UserRepository users; private final MatchRepository matches;
  public TournamentService(TournamentRepository t,UserRepository u,MatchRepository m){tournaments=t;users=u;matches=m;}
  public List<Tournament> upcoming(){return tournaments.findTop20ByStatusOrderByStartsAtAsc(TournamentStatus.REGISTRATION);}
  public List<Tournament> all(){return tournaments.findAll();}
  public Tournament create(CreateRequest r){Tournament t=new Tournament();t.setTitle(r.title());t.setGame(r.game());t.setDescription(r.description());if(r.maxPlayers()!=null)t.setMaxPlayers(r.maxPlayers());t.setRegistrationClosesAt(r.registrationClosesAt());t.setStartsAt(r.startsAt());t.setStatus(TournamentStatus.REGISTRATION);return tournaments.save(t);}
  public Tournament get(String id){return tournaments.findById(id).orElseThrow(()->new NoSuchElementException("Tournament not found"));}
  public Tournament register(String id,String userId){Tournament t=get(id); if(t.getStatus()!=TournamentStatus.REGISTRATION)throw new IllegalStateException("Registration is closed"); if(t.getPlayerIds().contains(userId))return t; if(t.getPlayerIds().size()>=t.getMaxPlayers())throw new IllegalStateException("Tournament is full"); t.getPlayerIds().add(userId); return tournaments.save(t);}
  public Tournament status(String id,StatusRequest r){Tournament t=get(id);t.setStatus(r.status());return tournaments.save(t);}
  public Tournament finish(String id,WinnerRequest r){Tournament t=get(id); if(!t.getPlayerIds().contains(r.winnerId()))throw new IllegalArgumentException("Winner is not registered"); t.setWinnerId(r.winnerId());t.setRunnerUpId(r.runnerUpId());t.setStatus(TournamentStatus.COMPLETED);users.findById(r.winnerId()).ifPresent(u->{u.setXp(u.getXp()+t.getWinnerXp());u.setTournamentWins(u.getTournamentWins()+1);users.save(u);}); if(r.runnerUpId()!=null)users.findById(r.runnerUpId()).ifPresent(u->{u.setXp(u.getXp()+t.getRunnerUpXp());u.setRunnerUps(u.getRunnerUps()+1);users.save(u);}); return tournaments.save(t);}
  public List<Match> matches(String id){return matches.findByTournamentId(id);}
  public Match report(String id,String userId,String winnerId,String evidence){Match m=matches.findById(id).orElseThrow();if(!userId.equals(m.getPlayer1Id())&&!userId.equals(m.getPlayer2Id()))throw new SecurityException("Not a participant");if(!winnerId.equals(m.getPlayer1Id())&&!winnerId.equals(m.getPlayer2Id()))throw new IllegalArgumentException("Winner must be a participant");m.setWinnerId(winnerId);m.setEvidenceUrl(evidence);m.setReportedBy(userId);m.setStatus(MatchStatus.PENDING_RESULT);return matches.save(m);}
  public Match verify(String id){Match m=matches.findById(id).orElseThrow();if(m.getWinnerId()==null)throw new IllegalStateException("No winner reported");m.setStatus(MatchStatus.VERIFIED);return matches.save(m);}
}
