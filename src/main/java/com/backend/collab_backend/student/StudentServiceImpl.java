package com.backend.collab_backend.student;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {
  private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
  private final StudentRepository studentRepository;

  public StudentDTO convertRealToDTOStudent(Student student) {
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.firstName = student.getFirstName();
    studentDTO.lastName = student.getLastName();
    studentDTO.group = student.getGroupId();
    studentDTO.email = student.getEmail();
    studentDTO.specialty = student.getSpecialty();
    studentDTO.year = student.getYear();
    return studentDTO;
  }

  public Student convertDTOtoRealStudent(StudentDTO studentDTO) {
    Student student = studentRepository.findStudentByEmail(studentDTO.email).get();
    System.out.println("Converting student DTO to real: "+student.getId());
    student.setFirstName(studentDTO.firstName);
    student.setLastName(studentDTO.lastName);
    student.setGroupId(studentDTO.group);
    student.setEmail(studentDTO.email);
    student.setSpecialty(studentDTO.specialty);
    student.setYear(studentDTO.year);
    return student;
  }

  @Override
  public List<StudentDTO> getAllStudents() {

    List<Student> students = studentRepository.findAll();
    List<StudentDTO> studentDTOs = new ArrayList<>();
    logger.info("Get all students: found [{}] students.", students.size());
    for (Student student : students) {
      studentDTOs.add(convertRealToDTOStudent(student));
    }

    return studentDTOs;
  }

  public Long signinStudent(String login, String password) {
    if(login.contains("@")) {
      Optional<Student> student = studentRepository.findStudentByEmailAndPassword(login, password);
      if (student.isPresent()) {
        return student.get().getId();
      } else {return 0L;}
    } else {
      Optional<Student> student = studentRepository.findStudentByUsernameAndPassword(login, password);
      if (student.isPresent()) {
        return student.get().getId();
      } else {return 0L;}
    }
  }

  @Override
  public StudentDTO getStudentById(Long id) {
    Student student = studentRepository.findStudentById(id).orElseGet(Student::new);
    logger.info("Searching for student_id[{}] returned [{}] [{}]", id, student.getFirstName(), student.getLastName());
    return convertRealToDTOStudent(student);
  }

  @Override
  public StudentDTO createStudent(StudentDTO student) {
    Student student1 = convertDTOtoRealStudent(student);
    student1.setUsername(student1.getFirstName().toLowerCase().charAt(0)+student1.getLastName().toLowerCase());
    logger.info("Saving new student with username [{}]", student1.getUsername());
    studentRepository.save(student1);
    return student;
  }

  @Override
  public void deleteStudent(String email) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
    if (studentOptional.isPresent()) {
      Student student = studentOptional.get();
      logger.info("Deleting student with id [{}] and email [{}]", student.getId(), student.getEmail());
      studentRepository.deleteStudentById(student.getId());
    }

  }

  @Override
  public StudentDTO updateStudent(String email, StudentDTO student){
    Optional<Student> studentToUpdate = studentRepository.findStudentByEmail(email);
    if(studentToUpdate.isEmpty()){
      System.out.println("Could not update student");
      logger.info("Updating student: couldn't find student with email[{}]", student.email);
      return new StudentDTO();
    }
    System.out.println("Updating student "+student.email);
    Student studentToUpdateNew = studentToUpdate.get();
    studentToUpdateNew.setFirstName(student.firstName);
    studentToUpdateNew.setLastName(student.lastName);
    studentToUpdateNew.setGroupId(student.group);
    studentToUpdateNew.setEmail(student.email);
    studentToUpdateNew.setSpecialty(student.specialty);
    studentToUpdateNew.setYear(student.year);
    System.out.println("Saving student to db: "+ studentToUpdateNew.getId());
    logger.info("Updating student email[{}]. New email (if changed): {}",student.email, studentToUpdate.get().getEmail());
    studentRepository.save(studentToUpdateNew);
    return student;
  }
}
