package com.example.progressBoard.service;
import com.example.progressBoard.entity.Day;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.repository.DayRepository;
import com.example.progressBoard.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.example.progressBoard.service.UserService;
@Component
public class DayService {
    @Autowired
    private DayRepository dayRepository;

    @Autowired
    public UserService userService;
    @Autowired
    private UserRepository userRepository;
    public void addDay(ObjectId userId, Day newDay) {
        newDay.setUserId(userId);
        newDay.setId(new ObjectId());
        this.userService.addTaskToUser(userId, newDay.getId());
        dayRepository.save(newDay);
        updateUserProgress(userId);
    }
    public void updateDay(ObjectId userId, Day updatedDay) {
        Optional<Day> existing = dayRepository.findByUserIdAndDateFor(userId, updatedDay.getDateFor());
        if (existing.isPresent()) {
            Day dayToUpdate = existing.get();
            dayToUpdate.setTasks(updatedDay.getTasks());
            dayRepository.save(dayToUpdate);
            updateUserProgress(userId);
        } else {
            throw new RuntimeException("Day not found for the user on given date: " + updatedDay.getDateFor());
        }
    }
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate date;
    public List<Day.Task> getTasks(ObjectId userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return new ArrayList<>();
        User user = userOpt.get();
        List<ObjectId> dayIds = user.getDayIds();
        List<Day> days = dayRepository.findByIdIn(dayIds);
        for (Day day : days) {
            if (day.getDateFor().equals(LocalDate.now())) {
                return day.getTasks();
            }
        }
        return new ArrayList<>();
    }
    public List<Day.Task> getTasksByDate(ObjectId userId, LocalDate date) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return new ArrayList<>();
        User user = userOpt.get();
        List<ObjectId> dayIds = user.getDayIds();
        List<Day> days = dayRepository.findByIdIn(dayIds);
        for (Day day : days) {
            if (day.getDateFor().equals(date)) {
                return day.getTasks();
            }
        }
        return new ArrayList<>();
    }
    public boolean updateUserProgress(ObjectId userId) {
        List<Day.Task> tasks = getTasks(userId);
        if(tasks.isEmpty() || tasks == null) return false;
        int total = tasks.size();
        long completed = tasks.stream().filter(Day.Task::isCompleted).count();
        Optional<Day> reqDay = dayRepository.findByUserIdAndDateFor(userId, LocalDate.now());
        double progress = (completed / (double) total) * 100.0;
        System.out.println("Calculated Progress for user:" + userId);
        if(reqDay.isPresent()) {
            System.out.println("Progress saved for user: " + userId);
            Day okDay = reqDay.get();
            okDay.setProgress(progress);
            dayRepository.save(okDay);
            return true;
        }
        return false;
    }
}
