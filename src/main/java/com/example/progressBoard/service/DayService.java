package com.example.progressBoard.service;
import com.example.progressBoard.entity.Day;
import com.example.progressBoard.repository.DayRepository;
import com.example.progressBoard.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import com.example.progressBoard.service.UserService;
@Component
public class DayService {
    @Autowired
    private DayRepository dayRepository;

    @Autowired
    public UserService userService;
    public void addDay(ObjectId userId, Day newDay) {
        newDay.setUserId(userId);
        newDay.setId(new ObjectId());
        this.userService.addTaskToUser(userId, newDay.getId());
        dayRepository.save(newDay);
    }
    public void updateDay(ObjectId userId, Day updatedDay) {
        Optional<Day> existing = dayRepository.findByUserIdAndDateFor(userId, updatedDay.getDateFor());
        if (existing.isPresent()) {
            Day dayToUpdate = existing.get();
            dayToUpdate.setTasks(updatedDay.getTasks());
            dayRepository.save(dayToUpdate);
        } else {
            throw new RuntimeException("Day not found for the user on given date.");
        }
    }

    public List<Day.Task> getTasks(ObjectId UserId) {
        Optional<Day> day = dayRepository.findById(UserId);
        if(day.isPresent()) {
            Day okDay = day.get();
            return okDay.getTasks();
        }
        return null;
    }
}
