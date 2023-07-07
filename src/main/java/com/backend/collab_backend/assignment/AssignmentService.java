package com.backend.collab_backend.assignment;


import java.util.List;

public interface AssignmentService {
  List<AssignmentDTO> findAllByGroup(String group);

  AssignmentDTO findAssignmentById(Long id);
  Assignment findByTitleAndDescription(String title, String description);
  AssignmentDTO createAssignment(AssignmentDTO assignment);
  AssignmentDTO updateAssignment(Long assignId, AssignmentDTO assignment);

  void deleteAssignment(Long id);
}

