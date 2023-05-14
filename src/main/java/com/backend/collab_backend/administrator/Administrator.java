package com.backend.collab_backend.administrator;

import com.backend.collab_backend.AbstractUser;
import com.backend.collab_backend.config.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Administrator extends AbstractUser {
  @Column(name = "specialty", nullable = false)
  private String specialty;

  @Column(name = "role", nullable = false)
  private static ERole role = ERole.ADMIN;

}
