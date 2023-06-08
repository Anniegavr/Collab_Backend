package com.backend.collab_backend;

import com.backend.collab_backend.administrator.AdministratorService;
import com.backend.collab_backend.role.ERole;
import com.backend.collab_backend.student.StudentService;
import com.backend.collab_backend.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AbstractUserServiceImpl implements AbstractUserService{
  private final StudentService studentService;
  private final TeacherService teacherService;
  private final AdministratorService administratorService;

  public Long signin(String login, String password, String role) {
    Long userId = 0L;
    if (role.equals("ADMIN")) {
      userId = administratorService.signinAdmin(login, password);
    } else if (Objects.equals(role, ERole.STUDENT.name())) {
      userId = studentService.signinStudent(login, password);
    } else if (Objects.equals(role, ERole.TEACHER.name())) {
      userId = teacherService.signinTeacher(login, password);
    }
    return userId;
  }


}
