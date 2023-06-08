
package com.backend.collab_backend.teacher;

import com.backend.collab_backend.AbstractUser;
import com.backend.collab_backend.role.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
public class TeacherDTO {
  public String firstName;
  public String lastName;
  public String email;
  public String specialty;

}
