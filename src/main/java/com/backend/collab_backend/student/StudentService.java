package com.backend.collab_backend.student;

import java.util.List;

public interface StudentService {
  List<StudentDTO> getAllStudents();
  StudentDTO getStudentById(Long id);

  StudentDTO createStudent(StudentDTO student);
  void deleteStudent(Long id);

  Long signinStudent(String login, String password);

  StudentDTO updateStudent(Long id, StudentDTO student);
}
