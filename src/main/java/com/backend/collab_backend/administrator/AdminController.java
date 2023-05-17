//*************************************************//
//          INTHER LOGISTICS ENGINEERING           //
//*************************************************//

package com.backend.collab_backend.administrator;

import com.backend.collab_backend.assignment.EAssignmentType;
import com.backend.collab_backend.student.StudentDTO;
import com.backend.collab_backend.teacher.TeacherDTO;
import lombok.RequiredArgsConstructor;
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

  private final AdministratorService administratorService;
  @PostMapping("/add_admin")
  public ResponseEntity<String> createAdministrator(AdministratorDTO administratorDTO) {
    administratorService.createAdministrator(administratorDTO);
    return ResponseEntity.ok("Success");
  }

  @PostMapping("/students/add")
  public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO student) {

    return ResponseEntity.ok(student);
  }

  @PostMapping("/teachers/add")
  public ResponseEntity<TeacherDTO> createStudent(@RequestBody TeacherDTO teacher) {
    // mock implementation
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

  @GetMapping
  public ResponseEntity<String> getAllAdministrators() {
    return ResponseEntity.ok("Success");
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
