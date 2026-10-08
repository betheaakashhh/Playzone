package com.playzone.api.controller;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.playzone.api.dto.MatchDtos.ResultRequest;
import com.playzone.api.model.Match;
import com.playzone.api.service.TournamentService;
@RestController @RequestMapping("/api/matches") public class MatchController {private final TournamentService s;public MatchController(TournamentService s){this.s=s;} @GetMapping("/tournament/{id}") public List<Match> list(@PathVariable String id){return s.matches(id);} @PostMapping("/{id}/result") public Match result(@PathVariable String id,@RequestBody ResultRequest r,Authentication a){return s.report(id,a.getName(),r.winnerId(),r.evidenceUrl());} @PreAuthorize("hasRole('ADMIN')") @PostMapping("/{id}/verify") public Match verify(@PathVariable String id){return s.verify(id);} }
