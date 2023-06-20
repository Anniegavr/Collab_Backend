package com.backend.collab_backend.administrator;

import com.backend.collab_backend.role.ERole;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdministratorDTO {
  public String firstName;
  public String lastName;
  public String email;
  public String specialty;
}
