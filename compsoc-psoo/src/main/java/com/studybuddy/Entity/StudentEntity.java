package com.studybuddy.Entity;
import com.studybuddy.entity.ResourceEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = true)
    private UserEntity userEntity;

    @Column(nullable = false)
    private String name;

    private String university;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyflowEntity> studyflowEntities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public List<StudyflowEntity> getStudyflowEntities() {
        return studyflowEntities;
    }

    public void setStudyflowEntities(List<StudyflowEntity> studyflowEntities) {
        this.studyflowEntities = studyflowEntities;
    }
}
