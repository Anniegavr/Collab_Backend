package com.backend.collab_backend.assignment.to.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupToScheduleRepository extends JpaRepository<GroupToSchedule, Long> {
  Optional<GroupToSchedule> findAssignmentToScheduleById(Long id);
  Optional<GroupToSchedule> findGroupToScheduleByGroupId(String groupId);
}
