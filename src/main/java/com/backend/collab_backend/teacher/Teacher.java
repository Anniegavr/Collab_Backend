package com.backend.collab_backend.teacher;

import com.backend.collab_backend.AbstractUser;
import com.backend.collab_backend.config.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Teacher extends AbstractUser {
  @ElementCollection
  private List<String> courses;

  @Column(name = "role", nullable = false)
  private ERole role = ERole.TEACHER;
}
