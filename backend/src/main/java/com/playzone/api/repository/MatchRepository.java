package com.playzone.api.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.playzone.api.model.Match;
public interface MatchRepository extends MongoRepository<Match,String> { List<Match> findByTournamentId(String tournamentId); }
