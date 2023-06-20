package com.backend.collab_backend.student;

import java.util.List;

public interface StudentService {
  List<StudentDTO> getAllStudents();
  StudentDTO getStudentById(Long id);

  StudentDTO createStudent(StudentDTO student);
  void deleteStudent(String email);

  Long signinStudent(String login, String password);

  StudentDTO updateStudent(String email, StudentDTO student);
}
