package com.example.progressBoard.repository;

import com.example.progressBoard.entity.Day;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DayRepository extends MongoRepository<Day, ObjectId> {
    Optional<Day> findByUserIdAndDateFor(ObjectId userId, LocalDate dateFor);
}
