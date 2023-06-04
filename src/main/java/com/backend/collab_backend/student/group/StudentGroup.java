package com.backend.collab_backend.student.group;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class StudentGroup {
  @Id
  private String name;
  private int year;
  private String email;
  private String specialty;
  private int startYear;
  private int freeTime;
  private int tripTime;
}
