package com.backend.collab_backend.teacher;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherService {
  List<TeacherDTO> getAllTeachers();
  TeacherDTO getTeacherByTeacherId(Long id);

  Long signinTeacher(String login, String password);
  ResponseEntity<String> createTeacher(TeacherDTO teacher);
  TeacherDTO updateTeacher(String email, TeacherDTO teacher);
  void deleteTeacher(String email);
}
