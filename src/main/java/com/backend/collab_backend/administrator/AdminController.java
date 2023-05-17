//*************************************************//
//          INTHER LOGISTICS ENGINEERING           //
//*************************************************//

package com.backend.collab_backend.administrator;

import com.backend.collab_backend.assignment.EAssignmentType;
import com.backend.collab_backend.student.Student;
import com.backend.collab_backend.student.StudentDTO;
import com.backend.collab_backend.student.StudentService;
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
  @PostMapping("/add_admin")
  public ResponseEntity<String> createAdministrator(AdministratorDTO administratorDTO) {
    administratorService.createAdministrator(administratorDTO);
    return ResponseEntity.ok("Success");
  }

  @PostMapping("/add_teacher")
  public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacher) {
    teacherService.createTeacher(teacher);
    return ResponseEntity.ok(teacher);
  }

  @GetMapping("/assignment_types")
  public ResponseEntity<List<EAssignmentType>> getAllAssignmentTypes() {
    List<EAssignmentType> assignmentTypes = new ArrayList<>();
    assignmentTypes.add(EAssignmentType.LAB);
    assignmentTypes.add(EAssignmentType.PROJECT);
    assignmentTypes.add(EAssignmentType.READING);
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
    if (newList.contains(assignmentTypeOld)) {
      newList.remove(assignmentTypeOld);
    }
    return ResponseEntity.ok(newList);
  }

  @PostMapping("/assignment_types/add")
  public ResponseEntity<List<String>> addAssignmentType(@RequestBody String newAssignmentType) {
    List<String> newList = new ArrayList<>();
    newList.add(EAssignmentType.LAB.name());
    newList.add(EAssignmentType.PROJECT.name());
    newList.add(EAssignmentType.READING.name());
    newList.add(newAssignmentType.replace('=', ' '));
    System.out.println("List with new type: "+newList);
    return ResponseEntity.ok(newList);
  }

  @PostMapping("/new_student")
  public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO student) {
    return ResponseEntity.ok(studentService.createStudent(student));
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO student) throws Exception {
    StudentDTO studentDTO = studentService.updateStudent(id, student);
    if(!studentDTO.equals(new StudentDTO())) {
      return ResponseEntity.ok(studentDTO);
    }
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public void deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
  }

  @GetMapping("/all")
  public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
    return ResponseEntity.ok(administratorService.getAllAdministrators());
  }

  @GetMapping("/requests/total")
  public ResponseEntity<String> getTotalWebsiteRequests() {
    return ResponseEntity.ok("3,489,744");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getAdministratorByAdministratorId(@PathVariable Long id) {
    return ResponseEntity.ok("JonathanD: Dean, HR Manager");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateAdministrator(@PathVariable Long id, @RequestBody Integer body) {
    return ResponseEntity.ok("Success");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAdministrator(@PathVariable Long id) {
    return ResponseEntity.ok("Success, you deleted admin with ID "+id);
  }
}
