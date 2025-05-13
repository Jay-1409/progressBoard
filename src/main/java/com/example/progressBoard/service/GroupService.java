package com.example.progressBoard.service;

import com.example.progressBoard.entity.Day;
import com.example.progressBoard.entity.Group;
import com.example.progressBoard.entity.User;
import com.example.progressBoard.entity.groupUserData;
import com.example.progressBoard.repository.GroupRepository;
import com.example.progressBoard.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.*;
@Component
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    public void createGroup(Group newGroup) {
        groupRepository.save(newGroup);
        newGroup.setObId(newGroup.getId().toString());
        groupRepository.save(newGroup);
    }
    public void addMember(ObjectId groupID, ObjectId memberId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupID);
        if (optionalGroup.isPresent()) {
            Group reqGroup = optionalGroup.get();
            reqGroup.getMemberIds().add(memberId);
            groupRepository.save(reqGroup);
        } else {
            System.out.println("Group not found");
        }
    }
    public List<ObjectId> getAllMem(ObjectId groupId) {
        Optional<Group> currentGroup = groupRepository.findById(groupId);
        if(currentGroup.isPresent()){
            Group presentGroup = currentGroup.get();
//            System.out.println("Returning member IDs: " + presentGroup.getMemberIds());
        }
        return groupRepository.findById(groupId).get().getMemberIds();
    }
    public List<groupUserData> getAllMembersAndProgress(ObjectId groupId) {
        Optional<Group> reqGroup = groupRepository.findById(groupId);
        List<groupUserData> mem_prog = new ArrayList<>();
        if(reqGroup.isPresent()){
            Group activeGroup = reqGroup.get();
            List<ObjectId> mem = activeGroup.getMemberIds();
            for (ObjectId memberId : mem) {
                Optional<User> userOpt = userRepository.findById(memberId);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    double progress = user.getTodayProgress();
                    String username = user.getUsername();
                    groupUserData data = new groupUserData();
                    data.setUserName(username);
                    data.setUserProgress(progress);
                    mem_prog.add(data);
                }
            }
        }  else
            System.out.println("No group with ObjectId: " + groupId);
//        System.out.println("=== Group User Progress Data ===");
//        for (groupUserData data : mem_prog) {
//            System.out.println("Username  : " + data.getUserName());
//            System.out.println("Progress  : " + data.getUserProgress());
//            System.out.println("-------------------------------");
//        }
        return mem_prog;
    }

    public List<Group> fetchUsersAllGroups(ObjectId userId) {
        Optional<User> reqUser= userRepository.findById(userId);
        if(reqUser.isPresent()){
            User activeuser = reqUser.get();
            List<ObjectId> activeGroupIds = activeuser.getGroupIds();
            List<Group> activeGroups = new ArrayList<>();
            for (ObjectId groupId : activeGroupIds) {
                Optional<Group> group = groupRepository.findById(groupId);
                group.ifPresent(activeGroups::add);
            }
            return activeGroups;
        }
        return null;
    }
    public boolean deleteGroupForUser(ObjectId userId, ObjectId groupId) {
        Optional<User> reqUser = userRepository.findById(userId);
        if(reqUser.isPresent()) {
            User activeUser = reqUser.get();
            try {
                activeUser.getGroupIds().remove(groupId);
                userRepository.save(activeUser);
//                System.out.println("Removed user: " + userId + " from group: " + groupId);
            } catch (Exception e) {
                System.out.println("Failed to remove user: " + userId + " from group: " + groupId);
                return false;
            }
        } else return false;
        Optional<Group> reqGroup = groupRepository.findById(groupId);
        if(reqGroup.isPresent()){
            Group activeGroup = reqGroup.get();
            try {
                activeGroup.getMemberIds().remove(userId);
                groupRepository.save(activeGroup);
            } catch (Exception e) {
                System.out.println("Failed to remove user: " + userId + " from group: " + groupId);
                return false;
            }
        } else return false;
        return true;
    }
//    public void addUserToGroup_group(ObjectId UUID, ObjectId GUID) {
//        Optional<Group> oGrp = groupRepository.findById(GUID);
//        if(oGrp.isPresent()) {
//            Group grp = oGrp.get();
//            grp.getMemberIds().add(UUID);
//            groupRepository.save(grp);
//        }
//    }
}
