package com.backend.collab_backend.student.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
  List<Skill> findAllBySkillTypeContainingIgnoreCase(String skillLiteral);
  Optional<Skill> findSkillBySkillType(String skillName);
}
