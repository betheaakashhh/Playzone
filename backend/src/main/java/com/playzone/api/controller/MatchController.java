package com.playzone.api.controller;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.playzone.api.dto.MatchDtos.*;
import com.playzone.api.model.Match;
import com.playzone.api.service.TournamentService;
@RestController @RequestMapping("/api/matches") public class MatchController {
  private final TournamentService s; public MatchController(TournamentService s){this.s=s;}
  @PreAuthorize("hasRole('ADMIN')") @GetMapping("/admin/pending") public List<Match> pending(){return s.pendingMatches();}
  @GetMapping("/tournament/{id}") public List<Match> list(@PathVariable String id){return s.matches(id);}
  @GetMapping("/tournament/{id}/bracket") public List<Match> bracket(@PathVariable String id){return s.bracket(id);}
  @PostMapping("/{id}/result") public Match result(@PathVariable String id,@RequestBody ResultRequest r,Authentication a){return s.report(id,a.getName(),r.winnerId(),r.evidenceUrl());}
  @PostMapping("/{id}/dispute") public Match dispute(@PathVariable String id,@RequestBody DisputeRequest r,Authentication a){return s.dispute(id,a.getName(),r.reason());}
  @PreAuthorize("hasRole('ADMIN')") @PostMapping("/{id}/verify") public Match verify(@PathVariable String id){return s.verify(id);}
}
