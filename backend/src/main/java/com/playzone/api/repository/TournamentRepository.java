package com.playzone.api.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.playzone.api.model.Tournament;
import com.playzone.api.model.TournamentStatus;
public interface TournamentRepository extends MongoRepository<Tournament,String> { List<Tournament> findTop20ByStatusOrderByStartsAtAsc(TournamentStatus status); }
