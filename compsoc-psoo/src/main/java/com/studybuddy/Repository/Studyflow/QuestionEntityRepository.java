package com.studybuddy.Repository.Studyflow;

import com.studybuddy.Entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, UUID> {
    List<QuestionEntity> findAllByStudyFlowId(UUID studyFlowId);
}
