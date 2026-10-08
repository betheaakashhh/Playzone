package com.playzone.api.controller;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import com.playzone.api.model.User;
import com.playzone.api.dto.AuthDtos.UserResponse;
import com.playzone.api.repository.UserRepository;
@RestController @RequestMapping("/api") public class PublicController {private final UserRepository users;public PublicController(UserRepository u){users=u;} @GetMapping("/health") public Map<String,String> health(){return Map.of("status","ok","service","playzone-api");} @GetMapping("/leaderboard") public List<UserResponse> leaderboard(){return users.findAll().stream().sorted(Comparator.comparingInt(User::getXp).reversed()).limit(50).map(u->new UserResponse(u.getId(),null,u.getUsername(),u.getInstagramUsername(),u.getRole().name(),u.getXp(),u.getWins(),u.getLosses(),u.getTournamentWins(),u.getRunnerUps())).toList();}}
