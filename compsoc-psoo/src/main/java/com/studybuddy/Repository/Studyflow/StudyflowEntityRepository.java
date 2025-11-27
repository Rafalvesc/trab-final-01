package com.studybuddy.Repository.Studyflow;

import com.studybuddy.Entity.StudyflowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudyflowEntityRepository extends JpaRepository<StudyflowEntity, UUID> {
    List<StudyflowEntity> findAllByStudentEntity_Id(UUID studentEntityId);
}
