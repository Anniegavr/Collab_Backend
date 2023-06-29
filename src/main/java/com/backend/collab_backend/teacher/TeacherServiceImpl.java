package com.backend.collab_backend.teacher;

import com.backend.collab_backend.role.ERole;
import com.backend.collab_backend.student.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  @Override
  public List<TeacherDTO> getAllTeachers() {
    List<Teacher> teacherList = teacherRepository.findAll();
    List<TeacherDTO> teacherDTOList = new ArrayList<>();
    for (Teacher teacher : teacherList){
      TeacherDTO teacherDTO = new TeacherDTO();
      teacherDTO.firstName = (teacher.getFirstName());
      teacherDTO.lastName = (teacher.getLastName());
      teacherDTO.email = (teacher.getEmail());
      teacherDTO.specialty = (teacher.getSpecialty());
      teacherDTOList.add(teacherDTO);
    }
    return teacherDTOList;
  }

  public void deleteTeacher(String email) {
    Optional<Teacher> teacher = teacherRepository.findTeacherByEmail(email);
    teacher.ifPresent(teacherRepository::delete);
  }


  @Override
  public TeacherDTO getTeacherByTeacherId(Long id) {
    Teacher teacher =  teacherRepository.findById(id)
            .orElseGet(Teacher::new);
    TeacherDTO teacherDTO = new TeacherDTO();
    teacherDTO.firstName = (teacher.getFirstName());
    teacherDTO.lastName = (teacher.getLastName());
    teacherDTO.email = (teacher.getEmail());
    teacherDTO.specialty = (teacher.getSpecialty());
    return teacherDTO;
  }

  @Override
  public Long signinTeacher(String login, String password) {
    if(login.contains("@")) {
      Optional<Teacher> teacher = teacherRepository.findTeacherByEmailAndPassword(login, password);
      if (teacher.isPresent()) {
        return teacher.get().getId();
      } else {return 0L;}
    } else {
      Optional<Teacher> teacher = teacherRepository.findTeacherByUsernameAndPassword(login, password);
      if (teacher.isPresent()) {
        return teacher.get().getId();
      } else {return 0L;}
    }
  }

  @Override
  public ResponseEntity<String> createTeacher(TeacherDTO teacher) {
    Teacher teacherToBSaved = new Teacher();
    teacherToBSaved.setFirstName(teacher.firstName);
    teacherToBSaved.setLastName(teacher.lastName);
    teacherToBSaved.setEmail(teacher.email);
    teacherToBSaved.setRole(ERole.TEACHER.name());
    teacherToBSaved.setSpecialty(teacher.specialty);
    teacherRepository.save(teacherToBSaved);
    teacherToBSaved.setUsername(teacher.firstName.charAt(0)+teacher.lastName);
    teacherRepository.save(teacherToBSaved);
    return ResponseEntity.ok("Created teacher: "+teacher);
  }

  @Override
  public TeacherDTO updateTeacher(String email, TeacherDTO teacher) {
    Optional<Teacher> searched = teacherRepository.findTeacherByEmail(email);
    if(searched.isPresent()) {
      System.out.println("........");
      Teacher teacherToUpdate = searched.get();
      teacherToUpdate.setFirstName(teacher.firstName);
      teacherToUpdate.setLastName(teacher.lastName);
      teacherToUpdate.setEmail(teacher.email);
      teacherToUpdate.setRole(ERole.TEACHER.name());
      teacherToUpdate.setSpecialty(teacher.specialty);
      teacherRepository.save(teacherToUpdate);
    }
    return teacher;
  }

}
