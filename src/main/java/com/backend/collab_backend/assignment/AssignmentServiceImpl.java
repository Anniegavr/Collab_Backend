package com.backend.collab_backend.assignment;

import com.backend.collab_backend.student.progress.ProgressServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
  private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);
  private final AssignmentRepository assignmentRepository;

  @Override
  public List<AssignmentDTO> findAllByGroup(String group) {
    List<Assignment> allAssignments = assignmentRepository.findAllByGroupId(group);
    List<AssignmentDTO> returnListOfAssignments = new ArrayList<>();
    if (allAssignments.isEmpty()){
      return new ArrayList<>();
    } else {
      for (Assignment assignment : allAssignments){
        returnListOfAssignments.add(convertAssignmentToDTO(assignment));
      }
    }
    return returnListOfAssignments;
  }

  public AssignmentDTO findAssignmentById(Long id) {
    Assignment assignment = assignmentRepository.findById(id).orElseGet(Assignment::new);

    return convertAssignmentToDTO(assignment);
  }

  public AssignmentDTO convertAssignmentToDTO(Assignment assignment){
    return new AssignmentDTO(assignment.getCourse(),
                            assignment.getTitle(),
                            assignment.getDescription(),
                            assignment.getGroupId(),
                            assignment.getType(),
                            assignment.getTime(),
                            assignment.getDueDate(),
                            assignment.getTeacherName());
  }

  @Override
  public AssignmentDTO createAssignment(AssignmentDTO assignment) {
    // Shall take the teacher from the security context
//        TeacherDTO teacher = teacherService.getTeacherByTeacherId(assignment.getTeacherId());
//        if (teacher == null || !teacher.getCourses().contains(assignment.getDescription())) {
//            throw new RuntimeException("Teacher cannot create an assignment for this course.");
//        }

    Assignment assignment1 = new Assignment();
    assignment1.setTitle(assignment.title);
    assignment1.setDescription(assignment.description);
    assignment1.setCourse(assignment.course);
    assignment1.setGroupId(assignment.group);
    assignment1.setType(assignment.type);
    assignment1.setDueDate(assignment.dueDate);
    assignment1.setTime(assignment.time);
    assignment1.setTeacherName(assignment.teacherName);
    assignmentRepository.save(assignment1);
    return assignment;
  }

  public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignment) {
    Assignment assignmentToUpdate = assignmentRepository.findById(id).orElseGet(Assignment::new);
    assignmentToUpdate.setTitle(assignment.title);
    assignmentToUpdate.setDescription(assignment.description);
    assignmentToUpdate.setCourse(assignment.course);
    assignmentToUpdate.setGroupId(assignment.group);
    assignmentToUpdate.setType(assignment.type);
    assignmentToUpdate.setDueDate(assignment.dueDate);
    assignmentToUpdate.setTime(assignment.time);
    assignmentToUpdate.setTeacherName(assignment.teacherName);
    assignmentRepository.deleteById(id);
    assignmentToUpdate.setAssignmentId(id);

    assignmentRepository.save(assignmentToUpdate);
    return assignment;
  }

  public void deleteAssignment(Long id) {
    assignmentRepository.deleteById(id);
  }


}
