package com.backend.collab_backend.assignment.to.schedule;

public interface AssignScheduleService {
  AssignmentToSchedule findByAssignment(Long assignId);
  AssignmentToSchedule findBySchedule(Long scheduleId);
  void deleteByAssignment(Long assignId);
  void deleteBySchedule(Long scheduleId);

  AssignmentToSchedule create(AssignmentToSchedule assignmentToSchedule);
  AssignmentToSchedule edit(Long assignId, Long scheduleId);
}
