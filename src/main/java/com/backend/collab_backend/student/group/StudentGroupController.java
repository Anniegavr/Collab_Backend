package com.backend.collab_backend.student.group;

import com.backend.collab_backend.administrator.AdminController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/student_groups")
public class StudentGroupController {
  private static final Logger logger = LoggerFactory.getLogger(StudentGroupController.class);
  private final StudentGroupService studentGroupService;
  @GetMapping("/all")
  public ResponseEntity<List<StudentGroupDTO>> getAllGroups(){
    List<StudentGroupDTO> groups = studentGroupService.getAllGroups();
    logger.info("Fetching all groups...");
    if (groups.isEmpty()) {
      logger.info("No groups registered.");
      groups.add(new StudentGroupDTO( "FAF-191", "faf-191fcim@UTM.onmicrosoft.com", "Software Engineering", 2019, 2, 5, 4));
      groups.add(new StudentGroupDTO( "FAF-192", "faf-192fcim@UTM.onmicrosoft.com", "Software Engineering", 2019, 2, 5, 4));
      groups.add(new StudentGroupDTO( "FAF-193", "faf-193fcim@UTM.onmicrosoft.com", "Software Engineering", 2019, 2, 5, 4));
      groups.add(new StudentGroupDTO("FAF-194", "faf-194fcim@UTM.onmicrosoft.com", "Software Engineering", 2019, 2, 5, 4));
    }
    logger.info("Fetched [{}] groups.", groups.size());
    return ResponseEntity.ok(groups);
  }

  @PutMapping("/edit/{name}")
  public ResponseEntity<StudentGroupDTO> editGroup(@PathVariable("name") String name, @RequestBody StudentGroupDTO studentGroupDTO) {
    logger.info("Attempting to update student group [{}]", name);
    StudentGroupDTO editedGroup = studentGroupService.editGroup(name, studentGroupDTO);
    logger.info("Updated group.");
    return ResponseEntity.ok(editedGroup);
  }
}
