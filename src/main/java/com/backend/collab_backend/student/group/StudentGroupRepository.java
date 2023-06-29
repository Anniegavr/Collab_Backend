package com.backend.collab_backend.student.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, String> {
  List<StudentGroup> findAllByGroupIdContaining(String name);


  Optional<StudentGroup> findStudentGroupByGroupId(String name);
}
