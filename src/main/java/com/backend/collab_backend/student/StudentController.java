package com.backend.collab_backend.student;

import com.backend.collab_backend.assignment.Assignment;
import com.backend.collab_backend.assignment.AssignmentDTO;
import com.backend.collab_backend.assignment.AssignmentService;
import com.backend.collab_backend.assignment.to.schedule.AssignScheduleService;
import com.backend.collab_backend.assignment.to.schedule.assignment.set.AssignmentSet;
import com.backend.collab_backend.assignment.to.schedule.assignment.set.AssignmentSetService;
import com.backend.collab_backend.course.CourseDTO;
import com.backend.collab_backend.schedule.Schedule;
import com.backend.collab_backend.schedule.ScheduleRepository;
import com.backend.collab_backend.schedule.ScheduleService;
import com.backend.collab_backend.schedule.ScheduleTask;
import com.backend.collab_backend.student.group.StudentGroupDTO;
import com.backend.collab_backend.student.group.StudentGroupService;
import com.backend.collab_backend.student.progress.ProgressDTO;
import com.backend.collab_backend.student.progress.ProgressService;
import com.backend.collab_backend.student.skill.SkillService;
import com.backend.collab_backend.student.skill.accomplishment.Accomplishment;
import com.backend.collab_backend.student.skill.accomplishment.AccomplishmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentController {
  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
  private final StudentService studentService;
  private final StudentGroupService studentGroupService;
  private final AccomplishmentService accomplishmentService;
  private final AssignmentService assignmentService;
  private final AssignmentSetService assignmentSetService;
  private final ProgressService progressService;
  private final ScheduleService scheduleService;

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

  public List<ScheduleTask> getAltTasks() {
    List<ScheduleTask> allTasks = new ArrayList<>();
    allTasks.add(new ScheduleTask("LFPC", 4));
    allTasks.add(new ScheduleTask("PTR", 2));
    allTasks.add(new ScheduleTask( "PR",3));
    allTasks.add(new ScheduleTask("Free Time", 4));
    allTasks.add(new ScheduleTask("Class Time", 3));
    allTasks.add(new ScheduleTask("Trip", 1));
    allTasks.add(new ScheduleTask("Sleep", 7));
    return allTasks;
  }
  @GetMapping("/{id}/schedule")
  public ResponseEntity<List<ScheduleTask>> getAllStudentTasks(@PathVariable Long id) {
    logger.info("Received request to view schedule tasks for student_id {} ",id);
    StudentDTO studentDTO = studentService.getStudentById(id);
    if (!studentDTO.group.equals("")) {
      Schedule schedule = scheduleService.getScheduleByGroupIdAndDate(studentDTO.group, LocalDate.now());
      if (schedule == null) {
        return ResponseEntity.ok(getAltTasks());
      } else {
        StudentGroupDTO groupDTO = studentGroupService.findGroup(studentDTO.group);
        int sleepTime = groupDTO.sleepTime;
        int classTime = groupDTO.classTime;
        int tripTime = groupDTO.tripTime;
        int freeTime = groupDTO.freeTime;
        int homeworkTime = 0;
        Long assignmentsSet = schedule.getAssignmentSetId();
        if (assignmentsSet != null) {
          List<AssignmentSet> assignmentSets = assignmentSetService.findAllSetsBySetId(assignmentsSet);
          if (assignmentSets.isEmpty()) {
            return ResponseEntity.ok(getAltTasks());
          }
          List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
          for (AssignmentSet set : assignmentSets) {
            AssignmentDTO assignmentDTO = assignmentService.findAssignmentById(set.getAssignmentId());
            if (assignmentDTO.title.equals("") && assignmentDTO.description.equals("") && assignmentDTO.time.equals("")) {
            } else {
              assignmentDTOS.add(assignmentDTO);
            }
          }
          if (assignmentDTOS.isEmpty()) {
            return ResponseEntity.ok(getAltTasks());
          }
          List <ScheduleTask> scheduleTasks = new ArrayList<>();
          for (AssignmentDTO assignmentDTO : assignmentDTOS) {
            Assignment assignment = assignmentService.findByTitleAndDescription(assignmentDTO.title, assignmentDTO.description);
            int spendForTask = assignmentSetService.findByAssignmentId(assignment.getAssignmentId()).getTimeToSpend();
            scheduleTasks.add(new ScheduleTask(assignmentDTO.title, spendForTask));
            homeworkTime += spendForTask;
          }
          int calculatedTotalHrs = sleepTime+classTime+tripTime+homeworkTime;
           if (calculatedTotalHrs < 24) {
            freeTime += 24-calculatedTotalHrs;
          } else if (calculatedTotalHrs > 24 ) {
            freeTime -= calculatedTotalHrs - 24;
          }
          scheduleTasks.add(new ScheduleTask("Sleep", sleepTime));
          scheduleTasks.add(new ScheduleTask("Class Time", classTime));
          scheduleTasks.add(new ScheduleTask("Trip Time", tripTime));
          scheduleTasks.add(new ScheduleTask("Free Time", freeTime));
          return ResponseEntity.ok(scheduleTasks);
        }

        return ResponseEntity.ok(getAltTasks());
      }
    }
    return ResponseEntity.ok(getAltTasks());
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

  @PostMapping("/{id}/add_skill")
  public ResponseEntity<String> addAccomplishment(@PathVariable("id") Long studentId,@RequestBody Accomplishment accomplishment) {
    logger.info("Adding accomplishment for student_id[{}]", studentId);
    return ResponseEntity.ok(accomplishmentService.addAccomplishment(studentId, accomplishment.getStudentAccomplishment(), accomplishment.getSkillType()));
  }

  @GetMapping("/{id}/accomplishments")
  public ResponseEntity<List<Accomplishment>> studentsAccomplishments(@PathVariable("id") Long studentId) {
    logger.info("Searching for accomplishments for student_id[{}]", studentId);
    return ResponseEntity.ok(accomplishmentService.findByStudent(studentId));
  }

}
