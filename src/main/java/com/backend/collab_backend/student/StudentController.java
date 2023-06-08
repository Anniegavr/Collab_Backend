package com.backend.collab_backend.student;

import com.backend.collab_backend.assignment.Assignment;
import com.backend.collab_backend.assignment.AssignmentDTO;
import com.backend.collab_backend.assignment.AssignmentService;
import com.backend.collab_backend.course.CourseDTO;
import com.backend.collab_backend.schedule.ScheduleTask;
import com.backend.collab_backend.student.progress.Progress;
import com.backend.collab_backend.student.progress.ProgressDTO;
import com.backend.collab_backend.student.progress.ProgressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentController {
  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
  private final StudentService studentService;
  private final AssignmentService assignmentService;
  private final ProgressService progressService;

  @GetMapping
  public List<StudentDTO> getAllStudents() {
    logger.info("Getting all students");
    return studentService.getAllStudents();
  }

  @GetMapping("/{id}")
  public StudentDTO getStudentById(@PathVariable Long id) {
    logger.info("Getting student with ID: {}", id);
    return studentService.getStudentById(id);
  }

  @GetMapping("/{id}/assignments")
  public ResponseEntity<List<AssignmentDTO>> getAllStudentAssignments(@PathVariable Long id) {
    String group = studentService.getStudentById(id).group;
    List<AssignmentDTO> allAssignments = assignmentService.findAllByGroup(group);
    logger.info("Received request to view assignments for student_id[{}]",id);
    if (allAssignments.isEmpty()) {
      allAssignments.add(new AssignmentDTO("Web Programming", "Lab 3", "Make a GUI", "FAF-191", "LAB", "5h", LocalDate.of(2023, 5, 19), "Ana Bejan"));
      allAssignments.add(new AssignmentDTO( "LFPC", "Project 2", "Convert a final automata into an NFA", "FAF-191", "PROJECT",  "2h", LocalDate.of(2023, 3, 15), "Darius Flocea"));
      allAssignments.add(new AssignmentDTO( "Graphic Design", "Chapter 10", "FAF-191", "READING",  "1h", "2023, 03, 13", LocalDate.of(2023, 5, 18), "Matei Corjan"));
      allAssignments.add(new AssignmentDTO( "Computation & Complexity", "Gr. Pr. 2", "FAF-191", "PROJECT",  "PROJECT","15h", LocalDate.of(2023, 3, 20), "Anatolii Gheorghiu"));
    }

    return ResponseEntity.ok(allAssignments);
  }
  @GetMapping("/{id}/courses")
  public ResponseEntity<List<CourseDTO>> getAllStudentCourses(@PathVariable Long id) {
    List<CourseDTO> allCourses = new ArrayList<>();
    allCourses.add(new CourseDTO("Computer Science", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwallpapercave.com%2Fwp%2Fwp2105387.jpg&f=1&nofb=1&ipt=ee6ba2c90701a38e1414810086dd1c6934c78b7d861347f1ca81923867597caa&ipo=images"));
    allCourses.add(new CourseDTO("Games Development", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.techrevolve.com%2Fwp-content%2Fuploads%2F2020%2F03%2FGame-Development-1068x601.png&f=1&nofb=1&ipt=a91e60644842fbe450c419ba61ceed87ca4a78c81b9d80abeba53cb0e82e4136&ipo=images"));
    allCourses.add(new CourseDTO("PBL", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.fastweb.com%2Fuploads%2Farticle_photo%2Fphoto%2F2036293%2Fcrop635w_the-5-students-you-meet-in-group-projects.jpg&f=1&nofb=1&ipt=5daa686361e0a8f3ef12b2ae0dc1b3883ab7ec2744909a0a2075c13462b2428d&ipo=images"));
    allCourses.add(new CourseDTO("Math", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Frockcoderacademy.com%2Fwp-content%2Fuploads%2F2018%2F05%2Fcalculus.jpg&f=1&nofb=1&ipt=a62f19a2145e139023362cf47b9d2d964fb91c812a040c19bc0e3df9d0c8c57c&ipo=images"));
    allCourses.add(new CourseDTO("History", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.the-rampage.org%2Fwp-content%2Fuploads%2F2018%2F11%2Fbigstock-History-56161577-620x423.jpg&f=1&nofb=1&ipt=29cd4c613419c3f21b05838cb7693b7f88055557e0c5a633b3ab828cf5bc8831&ipo=images"));

    System.out.println("Received request to view courses for student_id = "+id);
    return ResponseEntity.ok(allCourses);
  }
  @GetMapping("/{id}/schedule")
  public ResponseEntity<List<ScheduleTask>> getAllStudentTasks(@PathVariable Long id) {
    List<ScheduleTask> allTasks = new ArrayList<>();
    allTasks.add(new ScheduleTask("LFPC", 4));
    allTasks.add(new ScheduleTask("PTR", 2));
    allTasks.add(new ScheduleTask( "PR",3));
    allTasks.add(new ScheduleTask("Free Time", 4));
    allTasks.add(new ScheduleTask("Class Time", 3));
    allTasks.add(new ScheduleTask("Trip", 1));
    allTasks.add(new ScheduleTask("Sleep", 7));
    logger.info("Received request to view schedule tasks for student_id {} ",id);
    System.out.println("Received request to view schedule tasks for student_id = "+id);
    return ResponseEntity.ok(allTasks);
  }
  @GetMapping("/{id}/progress")
  public ResponseEntity<List<ProgressDTO>> getStudentProgressById(@PathVariable Long id) {
    logger.info("Received request to see progress for student_id[{}]",id);
    List<ProgressDTO> progresses = new ArrayList<>();
    List<ProgressDTO> progressDTOS = progressService.findAllForStudent(id);
    if (progressDTOS.isEmpty()) {
      progresses.add(new ProgressDTO("LFPC Lab 3", 60));
      progresses.add(new ProgressDTO("Web Programming Lab 5", 50));
      progresses.add(new ProgressDTO("Real Time Programming Lab 2", 30));
      progresses.add(new ProgressDTO("Cyber Security Lab 4", 70));
      progresses.add(new ProgressDTO("Hakuna", 50));
      progresses.add(new ProgressDTO("Matana", 50));
      return ResponseEntity.ok(progresses);
    } else {
      return ResponseEntity.ok(progressDTOS);
    }
  }

}
