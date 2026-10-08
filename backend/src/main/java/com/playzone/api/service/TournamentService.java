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
  public List<Match> pendingMatches(){return matches.findAll().stream().filter(m->m.getStatus()==MatchStatus.PENDING_RESULT||m.getStatus()==MatchStatus.DISPUTED).toList();}

  public List<Match> bracket(String id){return matches.findByTournamentId(id).stream().sorted(Comparator.comparingInt(Match::getRound).thenComparingInt(Match::getPosition)).toList();}

  public Tournament start(String id){
    Tournament t=get(id);
    if(t.getPlayerIds().size()<2) throw new IllegalStateException("At least two players are required");
    if(t.getStatus()==TournamentStatus.LIVE) return t;
    if(t.getStatus()!=TournamentStatus.REGISTRATION) throw new IllegalStateException("Tournament cannot be started from its current status");
    matches.deleteAll(matches.findByTournamentId(id));
    List<String> players=new ArrayList<>(t.getPlayerIds()); Collections.shuffle(players);
    int bracketSize=1; while(bracketSize<players.size()) bracketSize*=2;
    int position=1;
    for(int i=0;i<bracketSize;i+=2){
      String p1=i<players.size()?players.get(i):null; String p2=i+1<players.size()?players.get(i+1):null;
      Match m=new Match(); m.setTournamentId(id); m.setRound(1); m.setPosition(position++); m.setPlayer1Id(p1); m.setPlayer2Id(p2);
      if(p1==null || p2==null){m.setWinnerId(p1!=null?p1:p2);m.setStatus(MatchStatus.VERIFIED);} else m.setStatus(MatchStatus.SCHEDULED);
      matches.save(m);
    }
    advanceByes(id,1);
    t.setStatus(TournamentStatus.LIVE); return tournaments.save(t);
  }

  private void advanceByes(String tournamentId,int round){
    List<Match> current=matches.findByTournamentIdAndRoundOrderByPositionAsc(tournamentId,round);
    for(Match m:current) if(m.getStatus()==MatchStatus.VERIFIED && m.getWinnerId()!=null) advanceWinner(tournamentId,m);
  }

  private void advanceWinner(String tournamentId,Match m){
    int nextRound=m.getRound()+1; int nextPosition=(m.getPosition()+1)/2;
    Match next=matches.findByTournamentIdAndRoundAndPosition(tournamentId,nextRound,nextPosition).orElseGet(()->{Match x=new Match();x.setTournamentId(tournamentId);x.setRound(nextRound);x.setPosition(nextPosition);x.setStatus(MatchStatus.SCHEDULED);return x;});
    if(m.getPosition()%2==1) next.setPlayer1Id(m.getWinnerId()); else next.setPlayer2Id(m.getWinnerId());
    if(next.getPlayer1Id()!=null && next.getPlayer2Id()!=null) {
      next.setStatus(MatchStatus.SCHEDULED);
      matches.save(next);
    } else if(next.getPlayer1Id()!=null || next.getPlayer2Id()!=null) {
      next.setWinnerId(next.getPlayer1Id()!=null?next.getPlayer1Id():next.getPlayer2Id());
      next.setStatus(MatchStatus.VERIFIED);
      matches.save(next);
      advanceWinner(tournamentId,next);
    } else {
      matches.save(next);
    }
  }

  public Match report(String id,String userId,String winnerId,String evidence){Match m=matches.findById(id).orElseThrow(()->new NoSuchElementException("Match not found"));if(!userId.equals(m.getPlayer1Id())&&!userId.equals(m.getPlayer2Id()))throw new SecurityException("Not a participant");if(!winnerId.equals(m.getPlayer1Id())&&!winnerId.equals(m.getPlayer2Id()))throw new IllegalArgumentException("Winner must be a participant");if(m.getStatus()!=MatchStatus.SCHEDULED && m.getStatus()!=MatchStatus.DISPUTED)throw new IllegalStateException("Match is not accepting results");m.setWinnerId(winnerId);m.setEvidenceUrl(evidence);m.setReportedBy(userId);m.setStatus(MatchStatus.PENDING_RESULT);return matches.save(m);}

  public Match dispute(String id,String userId,String reason){Match m=matches.findById(id).orElseThrow(()->new NoSuchElementException("Match not found"));if(!userId.equals(m.getPlayer1Id())&&!userId.equals(m.getPlayer2Id()))throw new SecurityException("Not a participant");if(m.getStatus()!=MatchStatus.PENDING_RESULT)throw new IllegalStateException("No pending result to dispute");m.setDisputeReason(reason);m.setStatus(MatchStatus.DISPUTED);return matches.save(m);}

  public Match verify(String id){Match m=matches.findById(id).orElseThrow(()->new NoSuchElementException("Match not found"));if(m.getWinnerId()==null)throw new IllegalStateException("No winner reported");if(m.getStatus()!=MatchStatus.PENDING_RESULT && m.getStatus()!=MatchStatus.DISPUTED)throw new IllegalStateException("Match is not awaiting verification");m.setStatus(MatchStatus.VERIFIED);users.findById(m.getWinnerId()).ifPresent(u->{u.setXp(u.getXp()+25);u.setWins(u.getWins()+1);users.save(u);});String loser=m.getWinnerId().equals(m.getPlayer1Id())?m.getPlayer2Id():m.getPlayer1Id();if(loser!=null)users.findById(loser).ifPresent(u->{u.setLosses(u.getLosses()+1);users.save(u);});matches.save(m);advanceWinner(m.getTournamentId(),m);return m;}
}
