package com.playzone.api.repository;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.playzone.api.model.User;
public interface UserRepository extends MongoRepository<User,String> { Optional<User> findByEmail(String email); Optional<User> findByUsername(String username); }
