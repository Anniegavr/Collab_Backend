package com.backend.collab_backend.assignment;

import com.backend.collab_backend.assignment.to.schedule.AssignScheduleService;
import com.backend.collab_backend.assignment.to.schedule.AssignmentToSchedule;
import com.backend.collab_backend.assignment.to.schedule.assignment.set.AssignmentSet;
import com.backend.collab_backend.assignment.to.schedule.assignment.set.AssignmentSetService;
import com.backend.collab_backend.schedule.Schedule;
import com.backend.collab_backend.schedule.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
  private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);
  private final AssignmentRepository assignmentRepository;
  private final ScheduleService scheduleService;
  private final AssignScheduleService assignScheduleService;
  private final AssignmentSetService assignmentSetService;

  @Override
  public List<AssignmentDTO> findAllByGroup(String group) {
    List<Assignment> allAssignments = assignmentRepository.findAllByGroupId(group);
    logger.info("Found [{}] assignments active for group[{}]", allAssignments.size(), group);
    List<AssignmentDTO> returnListOfAssignments = new ArrayList<>();
    if (allAssignments.isEmpty()){
      returnListOfAssignments.add(new AssignmentDTO("Web Programming", "Lab 3", "Make a GUI", "FAF-191", "LAB", "5h", LocalDate.of(2023, 5, 19), "Ana Bejan"));
      returnListOfAssignments.add(new AssignmentDTO( "LFPC", "Project 2", "Convert a final automata into an NFA", "FAF-191", "PROJECT",  "2h", LocalDate.of(2023, 3, 15), "Darius Flocea"));
      returnListOfAssignments.add(new AssignmentDTO( "Graphic Design", "Chapter 10", "FAF-191", "FAF-191", "READING",  "1h", LocalDate.of(2023, 5, 18), "Matei Corjan"));
      returnListOfAssignments.add(new AssignmentDTO( "Computation & Complexity", "Gr. Pr. 2", "FAF-191", "PROJECT",  "PROJECT","15h", LocalDate.of(2023, 3, 20), "Anatolii Gheorghiu"));
      return returnListOfAssignments;
    } else {
      for (Assignment assignment : allAssignments){
        returnListOfAssignments.add(convertAssignmentToDTO(assignment));
      }
    }
    return returnListOfAssignments;
  }

  public Assignment findByTitleAndDescription(String title, String description) {
    Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentByTitleAndDescription(title, description);
    return assignmentOptional.orElse(null);
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
    // Create the assignment entity
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

    // Calculate assignment schedule
    List<Schedule> existingSchedules = scheduleService.getScheduleByGroupId(assignment.group);
    if (existingSchedules != null && !existingSchedules.isEmpty()) {
      AssignmentSet assignmentSet = null;

      // Iterate through existing schedules
      for (Schedule existingSchedule : existingSchedules) {
        assignmentSet = assignmentSetService.findBySetId(existingSchedule.getAssignmentSetId());

        if (assignmentSet == null) {
          // Create a new assignment set if it doesn't exist
          assignmentSet = new AssignmentSet();
          assignmentSet.setAssignmentSetId(existingSchedule.getAssignmentSetId());
          assignmentSetService.addSet(assignmentSet);
        }

        // Create AssignmentToSchedule entity and save it
        AssignmentToSchedule assignmentToSchedule = new AssignmentToSchedule();
        assignmentToSchedule.setScheduleId(existingSchedule.getId());
        assignmentToSchedule.setAssignmentSetId(assignmentSet.getId());
        assignScheduleService.create(assignmentToSchedule);

        // Update the due date if needed
        if (!assignment1.getDueDate().equals(assignment.dueDate)) {
          assignment1.setDueDate(assignment.dueDate);
          assignmentRepository.save(assignment1);
        }

        try {
          // Update the existing schedule to include the allocated time for the new assignment each day
          updateScheduleWithAssignment(existingSchedule, assignmentSet.getAssignmentSetId(), assignment.time, assignment.dueDate);
        } catch (Exception e) {
          return new AssignmentDTO("", "NoSuitableSchedule", "No suitable schedule was found for this assignment. Consider releasing it later or adjusting the due date.",
                                          "", "", "", LocalDate.now(), "");
        }
      }
    } else {
      // Generate schedules for upcoming days without existing schedules
      LocalDate startDate = LocalDate.now();
      LocalDate dueDate = assignment.dueDate;
      String timePerDay = calculateTimePerDay(null, assignment.time, startDate, dueDate);
      if (timePerDay != null) {
        while (!startDate.isAfter(dueDate)) {
          Schedule newSchedule = new Schedule();
          newSchedule.setDate(startDate);
          newSchedule.setFreeTimeLeft(timePerDay);
          newSchedule.setGroupId(assignment.group);
          scheduleService.createSchedule(newSchedule);

          AssignmentSet assignmentSet = new AssignmentSet();
          assignmentSet.setAssignmentSetId(newSchedule.getAssignmentSetId());
          assignmentSetService.addSet(assignmentSet);

          // Create AssignmentToSchedule entity and save it
          AssignmentToSchedule assignmentToSchedule = new AssignmentToSchedule();
          assignmentToSchedule.setScheduleId(newSchedule.getId());
          assignmentToSchedule.setAssignmentSetId(assignmentSet.getAssignmentSetId());
          assignScheduleService.create(assignmentToSchedule);

          startDate = startDate.plusDays(1);
        }
      } else {
        // No suitable schedule found
        return new AssignmentDTO("", "NoSuitableSchedule", "No suitable schedule was found for this assignment. Consider releasing it later or adjusting the due date.",
                                 "", "", "", LocalDate.now(), "");
      }
    }

    return assignment;
  }



  private void updateScheduleWithAssignment(Schedule schedule, Long assignmentSetId, String timePerDay, LocalDate dueDate) {
    LocalDate currentDate = LocalDate.now();
    while (!currentDate.isAfter(dueDate)) {
      Schedule existingSchedule = scheduleService.getScheduleByGroupIdAndDate(schedule.getGroupId(), currentDate);
      if (existingSchedule != null) {
        String existingFreeTimeLeft = existingSchedule.getFreeTimeLeft();

        Duration allocatedTime = Duration.parse(timePerDay);
        Duration existingFreeTime = Duration.parse(existingFreeTimeLeft);

        // Subtract the allocated time from the existing free time
        Duration updatedFreeTime = existingFreeTime.minus(allocatedTime);

        // Check if the updated free time is negative
        if (updatedFreeTime.isNegative()) {
          // Move the remaining allocated time to the next available day
          LocalDate nextAvailableDate = findNextAvailableDate(existingSchedule.getGroupId(), currentDate.plusDays(1), dueDate);
          if (nextAvailableDate != null) {
            Schedule nextSchedule = scheduleService.getScheduleByGroupIdAndDate(existingSchedule.getGroupId(), nextAvailableDate);
            if (nextSchedule != null) {
              String nextFreeTimeLeft = nextSchedule.getFreeTimeLeft();
              Duration nextFreeTime = Duration.parse(nextFreeTimeLeft);
              Duration remainingTime = updatedFreeTime.negated();

              // Subtract the remaining time from the next day's free time
              Duration updatedNextFreeTime = nextFreeTime.minus(remainingTime);
              nextSchedule.setFreeTimeLeft(updatedNextFreeTime.toString());
              scheduleService.updateSchedule(nextSchedule.getId(), nextSchedule);

              // Update the assignment set for the next day
              AssignmentSet nextAssignmentSet = assignmentSetService.findBySetId(nextSchedule.getAssignmentSetId());
              nextAssignmentSet.setAssignmentId(assignmentSetId);
              assignmentSetService.addSet(nextAssignmentSet);

              // Set the updated free time for the current day to zero
              updatedFreeTime = Duration.ZERO;
            }
          }
        }

        // Update the schedule with the updated free time
        existingSchedule.setFreeTimeLeft(updatedFreeTime.toString());
        scheduleService.updateSchedule(existingSchedule.getId(), existingSchedule);

        // Update the assignment set for the current day
        AssignmentSet assignmentSet = assignmentSetService.findBySetId(existingSchedule.getAssignmentSetId());
        assignmentSet.setAssignmentId(assignmentSetId);
        assignmentSetService.addSet(assignmentSet);
      }

      // Move to the next day
      currentDate = currentDate.plusDays(1);
    }
  }

  private LocalDate findNextAvailableDate(String groupId, LocalDate currentDate, LocalDate dueDate) {
    while (!currentDate.isAfter(dueDate)) {
      Schedule schedule = scheduleService.getScheduleByGroupIdAndDate(groupId, currentDate);
      if (schedule != null) {
        String freeTimeLeft = schedule.getFreeTimeLeft();
        Duration freeTime = Duration.parse(freeTimeLeft);
        if (freeTime.getSeconds() > 0) {
          // Sufficient free time available on this day
          return currentDate;
        }
      }
      // Move to the next day
      currentDate = currentDate.plusDays(1);
    }
    // No available date found within the due date range
    return null;
  }

  private String calculateTimePerDay(Schedule schedule, String totalEstimatedTime, LocalDate startDate, LocalDate dueDate) {
    // Calculate the total number of days between startDate and dueDate
    long totalDays = ChronoUnit.DAYS.between(startDate, dueDate);

    // Parse the totalEstimatedTime duration
    Duration totalEstimatedDuration;
    try {
      totalEstimatedDuration = Duration.parse(totalEstimatedTime);
    } catch (DateTimeParseException e) {
      // Handle the error case where totalEstimatedTime cannot be parsed
      return null;
    }
    long totalEstimatedTimeInSeconds = totalEstimatedDuration.getSeconds();

    // Parse the schedule's freeTimeLeft duration
    Duration freeTimeLeftDuration;
    if (schedule != null) {
      try {
        freeTimeLeftDuration = Duration.parse(schedule.getFreeTimeLeft());
      } catch (DateTimeParseException e) {
        // Handle the error case where schedule's freeTimeLeft cannot be parsed
        return null;
      }
    } else {
      // Handle the case where no existing schedule is provided
      return null;
    }
    long totalFreeTimePerDayInSeconds = freeTimeLeftDuration.getSeconds();

    // Calculate the time per day needed to complete the assignment
    long timePerDayInSeconds = totalEstimatedTimeInSeconds / totalDays;

    // Check if the available free time per day is sufficient
    if (timePerDayInSeconds <= totalFreeTimePerDayInSeconds) {
      Duration timePerDay = Duration.ofSeconds(timePerDayInSeconds);
      return timePerDay.toString();
    } else {
      // Not enough free time available per day
      return null;
    }
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
