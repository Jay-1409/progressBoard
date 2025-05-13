package com.example.progressBoard.repository;

import com.example.progressBoard.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
//    Optional<User> findByUsername(String username);
     Optional<User> findByEmail(String userEmail);
}

