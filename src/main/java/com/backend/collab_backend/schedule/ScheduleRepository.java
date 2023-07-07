package com.backend.collab_backend.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  Optional<Schedule> findScheduleByDate(LocalDate date);
  List<Schedule> findAllByGroupId(String id);
  Optional<Schedule> findScheduleByGroupIdAndDate(String grId, LocalDate date);

}
