package com.studybuddy.Entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import com.studybuddy.entity.ResourceEntity;

@Entity
@Table(name = "study_flows")
public class StudyflowEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private StudentEntity studentEntity;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column
    private Long timeSpent;

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceEntity> resourceEntities;

    @OneToMany(mappedBy = "studyFlow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ResourceEntity indicator;

    public ResourceEntity getIndicator() {
        return indicator;
    }

    public void setIndicator(ResourceEntity indicator) {
        this.indicator = indicator;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return studentEntity;
    }

    public void setStudent(StudentEntity student) {
        this.studentEntity = student;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<com.studybuddy.entity.ResourceEntity> getResources() {
        return resourceEntities;
    }

    public void setResources(List<com.studybuddy.entity.ResourceEntity> resourceEntities) {
        this.resourceEntities = resourceEntities;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}
