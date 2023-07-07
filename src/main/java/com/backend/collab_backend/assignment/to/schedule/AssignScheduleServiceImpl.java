package com.backend.collab_backend.assignment.to.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignScheduleServiceImpl implements AssignScheduleService {
  private final AssignScheduleRepository assignScheduleRepository;

  @Override
  public AssignmentToSchedule findByAssignment(Long assignmentId) {
    return assignScheduleRepository.findAssignmentToScheduleByAssignmentSetId(assignmentId).orElse(null);
  }

  @Override
  public AssignmentToSchedule findBySchedule(Long scheduleId) {
    return assignScheduleRepository.findAssignmentToScheduleByScheduleId(scheduleId).orElse(null);
  }

  @Override
  public void deleteByAssignment(Long assignmentId) {
    AssignmentToSchedule assignmentToSchedule = findByAssignment(assignmentId);
    if (assignmentToSchedule != null) {
      assignScheduleRepository.delete(assignmentToSchedule);
    }
  }

  @Override
  public void deleteBySchedule(Long scheduleId) {
    AssignmentToSchedule assignmentToSchedule = findBySchedule(scheduleId);
    if (assignmentToSchedule != null) {
      assignScheduleRepository.delete(assignmentToSchedule);
    }
  }

  @Override
  public AssignmentToSchedule create(AssignmentToSchedule assignmentToSchedule) {
    return assignScheduleRepository.save(assignmentToSchedule);
  }

  @Override
  public AssignmentToSchedule edit(Long assignmentId, Long scheduleId) {
    AssignmentToSchedule assignmentToSchedule = findByAssignment(assignmentId);
    if (assignmentToSchedule != null) {
      assignmentToSchedule.setScheduleId(scheduleId);
      return assignScheduleRepository.save(assignmentToSchedule);
    }
    return null;
  }
}
