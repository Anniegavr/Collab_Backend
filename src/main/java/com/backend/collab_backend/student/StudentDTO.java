package com.backend.collab_backend.student;

import com.backend.collab_backend.role.ERole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentDTO {
  public String firstName;
  public  String lastName;
  public String group;
  public String email;
  public  String specialty;
  public Integer year;
  public String role = ERole.STUDENT.name();
}
