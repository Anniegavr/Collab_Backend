package com.backend.collab_backend.student.progress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
  List<Progress> findAllByStudentId(Long id);
  Optional<Progress> findProgressByAssignmentId(Long assgnId);
  Optional<Progress> findProgressByTaskNameAndStudentId(String name, Long id);
}
