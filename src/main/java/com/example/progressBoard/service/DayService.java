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
import com.example.progressBoard.entity.TaskProgress;
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
        if(doesTodayExists(userId)){
            updateDay(userId, newDay);
        } else {
            newDay.setUserId(userId);
            newDay.setId(new ObjectId());
//          System.out.println("New day is: " + newDay);
            this.userService.addTaskToUser(userId, newDay.getId());
            dayRepository.save(newDay);
            updateUserProgress(userId);
        }
    }
    public boolean doesTodayExists(ObjectId userId) {
        Optional<Day> reqDay = dayRepository.findByUserIdAndDateFor(userId, LocalDate.now());
        if(reqDay.isPresent()) return true;
        else return false;
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
    public TaskProgress getTasks(ObjectId userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return new TaskProgress(new ArrayList<>(), 0);
        User user = userOpt.get();
        List<ObjectId> dayIds = user.getDayIds();
        List<Day> days = dayRepository.findByIdIn(dayIds);
        for (Day day : days) {
//            System.out.println("DATE FOR: " + day.getId() + ":" + (day.getDateFor() != null ? day.getDateFor() : "field is empty"));
            if (day.getDateFor() != null && day.getDateFor().equals(LocalDate.now())) {
               return new TaskProgress(day.getTasks(), day.getProgress());
            }
        }
        return new TaskProgress(new ArrayList<>(), 0);
    }

//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate date;
//    public List<Day.Task> getTasks(ObjectId userId) {
//        Optional<User> userOpt = userRepository.findById(userId);
//        if (userOpt.isEmpty()) return new ArrayList<>();
//        User user = userOpt.get();
//        List<ObjectId> dayIds = user.getDayIds();
//        List<Day> days = dayRepository.findByIdIn(dayIds);
//        System.out.println("Today is: " + LocalDate.now());
//        for (Day day : days) {
//            if (day.getDateFor().equals(LocalDate.now())) {
//                System.out.println("Progress for this day : " + day.getProgress());
//                return day.getTasks();
//            }
//        }
//        return new ArrayList<>();
//    }
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
        List<Day.Task> tasks = getTasks(userId).getTasks();
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
            Optional<User> reqUser = userRepository.findById(userId);
            if(reqUser.isPresent()){
                User activeUser = reqUser.get();
                activeUser.setTodayProgress(progress);
                userRepository.save(activeUser);
            }
            return true;
        }
        return false;
    }
    public void updateUser_progress(ObjectId UUID, double newProgress) {
        // updates the progress in the user entity
        Optional<User> reqUser = userRepository.findById(UUID);
        if(reqUser.isPresent()){
            User activeUser = reqUser.get();
            activeUser.setTodayProgress(newProgress);
            userRepository.save(activeUser);
        }
    }
}
