package com.backend.collab_backend.assignment.to.schedule.assignment.set;

import java.util.List;

public interface AssignmentSetService {
  AssignmentSet findBySetId(Long setId);
  List<AssignmentSet> findAllSetsBySetId(Long id);

  AssignmentSet findByAssignmentId(Long id);
  AssignmentSet addSet(AssignmentSet assignmentSet);
  void deleteSetBySetId(Long setId);
}
