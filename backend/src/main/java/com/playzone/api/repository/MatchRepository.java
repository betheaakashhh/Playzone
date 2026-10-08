package com.playzone.api.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.playzone.api.model.Match;
public interface MatchRepository extends MongoRepository<Match,String> { List<Match> findByTournamentId(String tournamentId); List<Match> findByTournamentIdAndRoundOrderByPositionAsc(String tournamentId,int round); Optional<Match> findByTournamentIdAndRoundAndPosition(String tournamentId,int round,int position); }
