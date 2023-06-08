package com.backend.collab_backend.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  Optional<Teacher> findTeacherByEmailAndPassword(String email, String password);

  Optional<Teacher> findTeacherByEmail(String email);
  Optional<Teacher> findTeacherByUsernameAndPassword(String username, String password);
}