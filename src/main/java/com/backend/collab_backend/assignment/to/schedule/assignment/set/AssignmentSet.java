package com.backend.collab_backend.assignment.to.schedule.assignment.set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class AssignmentSet {
  @SequenceGenerator(
          name="assign_set_sequence",
          sequenceName = "assign_set_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "assign_set_sequence"
  )
  @Id
  private Long id;

  private Long assignmentSetId;
  private Long assignmentId;
  private int timeToSpend;
}
