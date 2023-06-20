package com.backend.collab_backend.administrator;

import com.backend.collab_backend.assignment.EAssignmentType;
import com.backend.collab_backend.schedule.Schedule;
import com.backend.collab_backend.schedule.ScheduleService;
import com.backend.collab_backend.student.StudentDTO;
import com.backend.collab_backend.student.StudentService;
import com.backend.collab_backend.student.group.StudentGroup;
import com.backend.collab_backend.student.group.StudentGroupDTO;
import com.backend.collab_backend.student.group.StudentGroupService;
import com.backend.collab_backend.teacher.TeacherDTO;
import com.backend.collab_backend.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
  private final AdministratorService administratorService;
  private final StudentService studentService;
  private final TeacherService teacherService;

  private final StudentGroupService studentGroupService;

  private final ScheduleService scheduleService;

  @GetMapping("/schedules")
  public ResponseEntity<List<Schedule>> getAllSchedules(){
    return ResponseEntity.ok(scheduleService.getAllSchedules());
  }

  @GetMapping("/groups")
  public ResponseEntity<List<StudentGroupDTO>> getAllGroups() {
    return ResponseEntity.ok(studentGroupService.getAllGroups());
  }

  @DeleteMapping("/groups/{id}/delete")
  public void deleteGroup(@PathVariable("id") String groupId) {
    studentGroupService.deleteGroup(groupId);
  }

  @PostMapping("/add_admin")
  public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody AdministratorDTO administratorDTO) {
    logger.info("Creating new administrator: {}", administratorDTO);
    AdministratorDTO administratorDTO1 = administratorService.createAdministrator(administratorDTO);
    logger.info("New administrator created successfully: [{}]", administratorDTO);
    return ResponseEntity.ok(administratorDTO1);
  }

  @GetMapping("/teachers")
  public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
    return ResponseEntity.ok(teacherService.getAllTeachers());
  }

  @PostMapping("/update_teacher/{email}")
  public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable("email") String email, @RequestBody TeacherDTO teacherDTO) {
    TeacherDTO teacherDTO1 = teacherService.updateTeacher(email, teacherDTO);
    if(!teacherDTO1.equals(new TeacherDTO())) {
      logger.info("Teacher with email {} updated", email);
      return ResponseEntity.ok(teacherDTO1);
    }
    logger.info("Teacher with email {} not found", email);
    return ResponseEntity.noContent().build();
  }
  @PostMapping("/add_teacher")
  public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacher) {
    logger.info("Creating new teacher: {}", teacher);
    teacherService.createTeacher(teacher);
    logger.info("New teacher created successfully: [{}]", teacher);
    return ResponseEntity.ok(teacher);
  }

  @DeleteMapping("delete_teacher/{email}")
  public void deleteTeacher(@PathVariable("email") String email) {
    teacherService.deleteTeacher(email);
  }

  @PostMapping("/add_student")
  public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO student) {
    logger.info("Creating new student: {} {}", student.firstName, student.lastName);
    return ResponseEntity.ok(studentService.createStudent(student));
  }
  @PutMapping("/update_student/{email}")
  public ResponseEntity<StudentDTO> updateStudent(@PathVariable String email, @RequestBody StudentDTO student) {
    StudentDTO studentDTO = studentService.updateStudent(email, student);
    if(!studentDTO.equals(new StudentDTO())) {
      logger.info("Student with ID {} updated", email);
      return ResponseEntity.ok(studentDTO);
    }
    logger.info("Student with ID {} not found", email);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/assignment_types")
  public ResponseEntity<List<EAssignmentType>> getAllAssignmentTypes() {
    logger.info("Getting all assignment types");
    List<EAssignmentType> assignmentTypes = new ArrayList<>();
    assignmentTypes.add(EAssignmentType.LAB);
    assignmentTypes.add(EAssignmentType.PROJECT);
    assignmentTypes.add(EAssignmentType.READING);
    logger.info("Returning all assignment types: {}", assignmentTypes);
    return ResponseEntity.ok(assignmentTypes);
  }

  @PutMapping("/assignment_types/edit")
  public ResponseEntity<List<String>> editAssignmentType(@RequestBody String assignmentType, @RequestBody String newAssignmentType) {
    String assignmentTypeOld = EAssignmentType.valueOf(assignmentType).name();
    List<String> newList = new ArrayList<>();
    newList.add(EAssignmentType.LAB.name());
    newList.add(EAssignmentType.PROJECT.name());
    newList.add(EAssignmentType.READING.name());
    newList.add(newAssignmentType);
    newList.remove(assignmentTypeOld);
    logger.info("Assignment type updated from {} to {}", assignmentTypeOld, newAssignmentType);
    return ResponseEntity.ok(newList);
  }
  @PostMapping("/assignment_types/add")
  public ResponseEntity<List<String>> addAssignmentType(@RequestBody String newAssignmentType) {
    List<String> newList = new ArrayList<>();
    newList.add(EAssignmentType.LAB.name());
    newList.add(EAssignmentType.PROJECT.name());
    newList.add(EAssignmentType.READING.name());
    newList.add(newAssignmentType.replace('=', ' '));
    logger.info("Assignment type added: {}", newAssignmentType);
    return ResponseEntity.ok(newList);
  }

  @PostMapping("/auth/signin")
  public ResponseEntity<String> authenticateUser(@RequestBody String request) {
    System.out.println("Got request for "+request);
    return ResponseEntity.ok("Success");
  }
  @DeleteMapping("/del_student/{email}")
  public void deleteStudent(@PathVariable String email) {
    logger.info("Deleting student with email {}", email);
    studentService.deleteStudent(email);
  }
  @GetMapping("/all_admins")
  public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
    logger.info("Received request to get all administrators");
    return ResponseEntity.ok(administratorService.getAllAdministrators());
  }
  @GetMapping("/requests/total")
  public ResponseEntity<String> getTotalWebsiteRequests() {
    logger.info("Received request to get total website requests");
    return ResponseEntity.ok("3,489,744");
  }
  @GetMapping("/admin/{email}")
  public ResponseEntity<String> getAdministratorByAdministratorId(@PathVariable String email) {
    logger.info("Received request to get administrator by ID: {}", email);
    administratorService.getAdministratorByAdministratorEmail(email);
    return ResponseEntity.ok("JonathanD: Dean, HR Manager");
  }
  @DeleteMapping("/delete_admin/{email}")
  public ResponseEntity<String> deleteAdministrator(@PathVariable String email) {
    administratorService.deleteAdministrator(email);
    logger.info("Received request to delete administrator with email: {}", email);
    return ResponseEntity.ok("Success, you deleted admin with email "+email);
  }

  @PutMapping("/edit_admin/{email}")
  public ResponseEntity<AdministratorDTO> editAdmin(@PathVariable String email, @RequestBody AdministratorDTO administratorDTO) {
    logger.info("Updating admin[{}]", administratorDTO.email);
    AdministratorDTO administratorDTO1 = administratorService.editAdmin(email, administratorDTO);
    logger.info("Now, admin has values [{}]", administratorDTO1);
    System.out.println("Edited admin");
    return ResponseEntity.ok(administratorDTO1);
  }
}
