package com.backend.collab_backend.assignment.to.schedule.assignment.set;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentSetRepository extends JpaRepository<AssignmentSet, Long> {
  Optional<AssignmentSet> findAssignmentSetByAssignmentSetId(Long setId);
  List<AssignmentSet> findAllByAssignmentSetId(Long setId);

  Optional<AssignmentSet> findAssignmentSetByAssignmentId(Long id);
}
