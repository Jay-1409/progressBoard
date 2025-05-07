package com.example.progressBoard.service;

import com.example.progressBoard.entity.Day;
import com.example.progressBoard.entity.Group;
import com.example.progressBoard.entity.User;
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

    public void createGroup(Group newGroup) {
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
    public List<ObjectId> getAll(ObjectId groupId) {
        Optional<Group> currentGroup = groupRepository.findById(groupId);
        if(currentGroup.isPresent()){
            Group presentGroup = currentGroup.get();
//            System.out.println("Returning member IDs: " + presentGroup.getMemberIds());
        }
        return groupRepository.findById(groupId).get().getMemberIds();
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
