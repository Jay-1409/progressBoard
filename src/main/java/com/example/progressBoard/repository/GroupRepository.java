package com.example.progressBoard.repository;

import com.example.progressBoard.entity.Group;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.*;
@Repository
public interface GroupRepository extends MongoRepository<Group, ObjectId> {
//    Optional<List<Group>> findAllByUserId(ObjectId userId);
}
