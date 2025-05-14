package com.example.progressBoard.repository;

import com.example.progressBoard.entity.Admin;
import com.example.progressBoard.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, ObjectId> {
    Optional<Admin> findByEmail(String userEmail);
}

