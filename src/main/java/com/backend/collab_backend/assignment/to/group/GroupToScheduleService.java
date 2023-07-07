package com.backend.collab_backend.assignment.to.group;

public interface GroupToScheduleService {
  GroupToSchedule findScheduleForGroup(String groupId);

  GroupToSchedule addScheduleForGroup(GroupToSchedule groupToSchedule);

  GroupToSchedule editGroupSchedule(GroupToSchedule groupToSchedule);
}
