package com.studybuddy.Repository.Student;

import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByUserEntity(UserEntity userEntity);
}
