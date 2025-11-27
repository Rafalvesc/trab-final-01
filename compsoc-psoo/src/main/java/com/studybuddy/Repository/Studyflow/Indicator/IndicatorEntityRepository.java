package com.studybuddy.Repository.Studyflow.Indicator;

import com.studybuddy.Entity.IndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IndicatorEntityRepository extends JpaRepository<IndicatorEntity, UUID> {
    List<IndicatorEntity> findAllByStudyflowId(UUID studyflowId);
}
