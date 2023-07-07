package com.backend.collab_backend.assignment.to.group;

import com.backend.collab_backend.schedule.Schedule;
import com.backend.collab_backend.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupToScheduleServiceImpl implements GroupToScheduleService {
  private static final Logger logger = LoggerFactory.getLogger(GroupToScheduleServiceImpl.class);

  private final GroupToScheduleRepository groupToScheduleRepository;
  private final ScheduleService scheduleService;
  public GroupToSchedule findScheduleForGroup(String groupId) {
    Optional<GroupToSchedule> schedule = groupToScheduleRepository.findGroupToScheduleByGroupId(groupId);
    if (schedule.isPresent()) {
      logger.info("Retrieved schedule for group[{}]", groupId);
      return schedule.get();
    } else {
      logger.info("Schedule not found. Generated new for group[{}]", groupId);
      List<Schedule> schedules = scheduleService.getScheduleByGroupId(groupId);
      if (!schedules.isEmpty()) {
        Schedule firstSchedule = schedules.get(0);
        GroupToSchedule groupToSchedule = new GroupToSchedule();
        groupToSchedule.setGroupId(groupId);
        groupToSchedule.setScheduleId(firstSchedule.getId());
        groupToScheduleRepository.save(groupToSchedule);
        return groupToSchedule;
      }
      return null; // or handle the case where no schedules are found
    }
  }


  public GroupToSchedule addScheduleForGroup(GroupToSchedule groupToSchedule) {
    logger.info("Adding schedule[{}] for group[{}]", groupToSchedule.getScheduleId(), groupToSchedule.getGroupId());
    return groupToScheduleRepository.save(groupToSchedule);
  }

  public GroupToSchedule editGroupSchedule(GroupToSchedule groupToSchedule) {
    Optional<GroupToSchedule> schedule = groupToScheduleRepository.findGroupToScheduleByGroupId(groupToSchedule.getGroupId());
    logger.info("Editing schedule for group[{}]", groupToSchedule.getGroupId());
    if (schedule.isPresent()) {
      GroupToSchedule schedule1 = schedule.get();
      logger.info("Retrieved old version schedule[{}] for group[{}]", schedule1, groupToSchedule.getGroupId());
      schedule1.setScheduleId(groupToSchedule.getScheduleId());
      schedule1.setGroupId(groupToSchedule.getGroupId());
      logger.info("New schedule is [{}]", schedule1);
      return groupToScheduleRepository.save(schedule1);
    } else {
      logger.info("Record doesn't exist, saving it as new one: schedule[{}]", groupToSchedule);
      return groupToScheduleRepository.save(groupToSchedule);
    }
  }

}
