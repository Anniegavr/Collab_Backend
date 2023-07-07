package com.backend.collab_backend.assignment.to.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class GroupToSchedule {
  @SequenceGenerator(
          name="gr_sch_sequence",
          sequenceName = "gr_sch_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "gr_sch_sequence"
  )
  @Id
  private Long id;

  @Unique
  @Column(nullable = false)
  private String groupId;

  @Unique
  @Column(nullable = false)
  private Long scheduleId;

}
