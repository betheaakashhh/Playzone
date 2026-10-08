package com.playzone.api.controller;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.playzone.api.dto.TournamentDtos.*;
import com.playzone.api.model.*;
import com.playzone.api.service.TournamentService;
@RestController @RequestMapping("/api/tournaments") public class TournamentController {
  private final TournamentService s; public TournamentController(TournamentService s){this.s=s;}
  @GetMapping public List<Tournament> upcoming(){return s.upcoming();}
  @PreAuthorize("hasRole('ADMIN')") @GetMapping("/all") public List<Tournament> all(){return s.all();}
  @GetMapping("/{id}") public Tournament get(@PathVariable String id){return s.get(id);}
  @PreAuthorize("hasRole('ADMIN')") @PostMapping public Tournament create(@RequestBody CreateRequest r){return s.create(r);}
  @PreAuthorize("isAuthenticated()") @PostMapping("/{id}/register") public Tournament register(@PathVariable String id,Authentication a){return s.register(id,a.getName());}
  @PreAuthorize("hasRole('ADMIN')") @PatchMapping("/{id}/status") public Tournament status(@PathVariable String id,@RequestBody StatusRequest r){return s.status(id,r);}
  @PreAuthorize("hasRole('ADMIN')") @PostMapping("/{id}/start") public Tournament start(@PathVariable String id){return s.start(id);}
  @PreAuthorize("hasRole('ADMIN')") @PostMapping("/{id}/finish") public Tournament finish(@PathVariable String id,@RequestBody WinnerRequest r){return s.finish(id,r);}
}
