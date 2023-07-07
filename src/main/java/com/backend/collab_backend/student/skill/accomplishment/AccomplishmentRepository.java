package com.backend.collab_backend.student.skill.accomplishment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccomplishmentRepository extends JpaRepository<Accomplishment, Long> {
  List<Accomplishment> findAllByStudentId(Long id);

  Optional<Accomplishment> findAccomplishmentByStudentIdAndStudentAccomplishment(Long id, String acc);

  List<Accomplishment> findAllBySkillType(String skillType);
  List<Accomplishment> findAllByStudentAccomplishmentContainingIgnoreCase(String search);
}
