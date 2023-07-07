package com.backend.collab_backend.assignment.to.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/assignments_schedule")
@RequiredArgsConstructor
public class AssignScheduleController {
  private final AssignScheduleService assignScheduleService;

  @GetMapping("/assignment/{assignmentId}")
  public ResponseEntity<AssignmentToSchedule> findByAssignment(@PathVariable Long assignmentId) {
    AssignmentToSchedule assignmentToSchedule = assignScheduleService.findByAssignment(assignmentId);
    if (assignmentToSchedule != null) {
      return ResponseEntity.ok(assignmentToSchedule);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/schedule/{scheduleId}")
  public ResponseEntity<AssignmentToSchedule> findBySchedule(@PathVariable Long scheduleId) {
    AssignmentToSchedule assignmentToSchedule = assignScheduleService.findBySchedule(scheduleId);
    if (assignmentToSchedule != null) {
      return ResponseEntity.ok(assignmentToSchedule);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/assignment/{assignmentId}")
  public ResponseEntity<Void> deleteByAssignment(@PathVariable Long assignmentId) {
    assignScheduleService.deleteByAssignment(assignmentId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/schedule/{scheduleId}")
  public ResponseEntity<Void> deleteBySchedule(@PathVariable Long scheduleId) {
    assignScheduleService.deleteBySchedule(scheduleId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<AssignmentToSchedule> create(@RequestBody AssignmentToSchedule assignmentToSchedule) {
    AssignmentToSchedule createdAssignment = assignScheduleService.create(assignmentToSchedule);
    return ResponseEntity.ok(createdAssignment);
  }

  @PutMapping("/assignment/{assignmentId}/schedule/{scheduleId}")
  public ResponseEntity<AssignmentToSchedule> edit(
          @PathVariable Long assignmentId,
          @PathVariable Long scheduleId
  ) {
    AssignmentToSchedule editedAssignment = assignScheduleService.edit(assignmentId, scheduleId);
    if (editedAssignment != null) {
      return ResponseEntity.ok(editedAssignment);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}

