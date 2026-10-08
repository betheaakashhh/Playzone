package com.playzone.api.controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.playzone.api.dto.AuthDtos.*;
import com.playzone.api.service.AuthService;
@RestController @RequestMapping("/api/auth") public class AuthController { private final AuthService auth; public AuthController(AuthService a){auth=a;} @PostMapping("/register") public AuthResponse register(@RequestBody RegisterRequest r){return auth.register(r);} @PostMapping("/login") public AuthResponse login(@RequestBody LoginRequest r){return auth.login(r);} }
