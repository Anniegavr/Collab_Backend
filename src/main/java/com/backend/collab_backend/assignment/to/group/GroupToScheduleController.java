package com.backend.collab_backend.assignment.to.group;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groupSchedule")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GroupToScheduleController {
  private final GroupToScheduleService groupToScheduleService;
  private static final Logger logger = LoggerFactory.getLogger(GroupToScheduleController.class);

  @GetMapping("/{groupId}")
  public ResponseEntity<GroupToSchedule> getScheduleForGroup(@PathVariable("groupId") String groupId) {
    logger.info("Retrieving schedule for group [{}]", groupId);
    return ResponseEntity.ok(groupToScheduleService.findScheduleForGroup(groupId));
  }

  @PostMapping("/addSchedule")
  public ResponseEntity<GroupToSchedule> addScheduleForGroup(@RequestBody GroupToSchedule groupToSchedule) {
    groupToScheduleService.addScheduleForGroup(groupToSchedule);
    return ResponseEntity.ok(groupToSchedule);
  }

  @PutMapping("/editSchedule/{groupId}")
  public ResponseEntity<GroupToSchedule> editGroupSchedule(@RequestBody GroupToSchedule groupToSchedule) {

    return ResponseEntity.ok(groupToSchedule);
  }
}
