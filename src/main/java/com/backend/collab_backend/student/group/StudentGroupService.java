package com.backend.collab_backend.student.group;

import java.util.List;

public interface StudentGroupService {
  List<StudentGroupDTO> getAllGroups();
  StudentGroupDTO addGroup(StudentGroupDTO studentGroupDTO);

  StudentGroupDTO editGroup(String name, StudentGroupDTO studentGroupDTO);

  StudentGroupDTO findGroup(String groupId);

  void deleteGroup(String id);
}
