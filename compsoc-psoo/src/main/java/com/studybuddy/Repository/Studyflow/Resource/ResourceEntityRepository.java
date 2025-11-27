package com.studybuddy.Repository.Studyflow.Resource;


import org.springframework.data.jpa.repository.JpaRepository;
import com.studybuddy.entity.ResourceEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceEntityRepository extends JpaRepository<ResourceEntity, UUID> {
    List<com.studybuddy.entity.ResourceEntity> findAllByStudyFlowId(UUID studyFlowId);

    Optional<com.studybuddy.entity.ResourceEntity> findByStudyFlowIdAndFilename(UUID studyFlowId, String filename);
}
