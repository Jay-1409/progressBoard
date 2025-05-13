package com.example.progressBoard.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "groups")
public class Group {
    @Id private ObjectId id;
    private String ObId;
    private String name;
    private String groupPass;
    private List<ObjectId> memberIds;

    public ObjectId getId() {
        return id;
    }

    public String getObId() {
        return ObId;
    }

    public void setObId(String obId) {
        ObId = obId;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupPass() {
        return groupPass;
    }

    public void setGroupPass(String groupPass) {
        this.groupPass = groupPass;
    }

    public List<ObjectId> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<ObjectId> memberIds) {
        this.memberIds = memberIds;
    }
}
