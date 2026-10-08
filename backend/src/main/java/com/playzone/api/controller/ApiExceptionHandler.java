package com.playzone.api.controller;
import java.util.NoSuchElementException;
import java.util.Map; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class ApiExceptionHandler { @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> bad(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));} @ExceptionHandler(NoSuchElementException.class) ResponseEntity<?> notFound(NoSuchElementException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));} @ExceptionHandler(IllegalStateException.class) ResponseEntity<?> state(IllegalStateException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));} }
