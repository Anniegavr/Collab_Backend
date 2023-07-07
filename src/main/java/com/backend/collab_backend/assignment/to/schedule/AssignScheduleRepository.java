package com.backend.collab_backend.assignment.to.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignScheduleRepository extends JpaRepository<AssignmentToSchedule, Long> {
  Optional<AssignmentToSchedule> findAssignmentToScheduleByScheduleId(Long scheduleId);

  Optional<AssignmentToSchedule> findAssignmentToScheduleByAssignmentSetId(Long setId);
}
