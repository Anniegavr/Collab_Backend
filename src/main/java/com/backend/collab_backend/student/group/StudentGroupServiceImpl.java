package com.backend.collab_backend.student.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {
  private final StudentGroupRepository studentGroupRepository;
  public List<StudentGroupDTO> getAllGroups() {
    List<StudentGroup> allGroups = studentGroupRepository.findAll();
    List<StudentGroupDTO> allGroupsDTOs = new ArrayList<>();
    for (StudentGroup group : allGroups) {
      allGroupsDTOs.add(new StudentGroupDTO(group.getGroupId(),
                                            group.getEmail(),
                                            group.getSpecialty(),
                                            group.getStartYear(),
                                            group.getTripTime(),
                                            group.getFreeTime(),
                                            group.getSleepTime(),
                                            group.getClassTime(),
                                            group.getYear()));
    }
    return allGroupsDTOs;
  }

  public StudentGroupDTO addGroup(StudentGroupDTO studentGroupDTO) {
    StudentGroup studentGroup = convertDTOToReal(studentGroupDTO);
    studentGroupRepository.save(studentGroup);
    return studentGroupDTO;
  }

  public StudentGroupDTO findGroup(String groupId) {
    Optional<StudentGroup> studentGroupOptional = studentGroupRepository.findStudentGroupByGroupId(groupId);
    if (studentGroupOptional.isPresent()) {
      return convertRealToDTO(studentGroupOptional.get());
    } else {
      return new StudentGroupDTO(groupId, groupId+"@isa.utm.md", "ISA", 2019, 2, 4, 7, 3, 4);
    }
  }

  public StudentGroupDTO editGroup(String name, StudentGroupDTO studentGroupDTO) {
    Optional<StudentGroup> studentGroupOptional = studentGroupRepository.findStudentGroupByGroupId(name);
    StudentGroup studentGroup;
    if (studentGroupOptional.isPresent()) {
      studentGroup = studentGroupOptional.get();
      studentGroup.setGroupId(studentGroupDTO.name);
      studentGroup.setEmail(studentGroupDTO.email);
      studentGroup.setYear(studentGroupDTO.year);
      studentGroup.setSpecialty(studentGroupDTO.specialty);
      studentGroup.setFreeTime(studentGroupDTO.freeTime);
      studentGroup.setTripTime(studentGroupDTO.tripTime);
      studentGroup.setStartYear(studentGroupDTO.startYear);
      studentGroupRepository.save(studentGroup);
    }
    return studentGroupDTO;
  }
  @Override
  public void deleteGroup(String id) {
    Optional<StudentGroup> studentGroup = studentGroupRepository.findStudentGroupByGroupId(id);
    studentGroup.ifPresent(studentGroupRepository::delete);
  }

  public StudentGroupDTO createGroup(StudentGroupDTO group) {
    StudentGroup newGroup = convertDTOToReal(group);
    studentGroupRepository.save(newGroup);
    return group;
  }

  public StudentGroupDTO convertRealToDTO(StudentGroup studentGroup) {
    return new StudentGroupDTO(
            studentGroup.getGroupId(),
            studentGroup.getEmail(),
            studentGroup.getSpecialty(),
            studentGroup.getStartYear(),
            studentGroup.getTripTime(),
            studentGroup.getFreeTime(),
            studentGroup.getSleepTime(),
            studentGroup.getClassTime(),
            studentGroup.getYear()
    );
  }

  public StudentGroup convertDTOToReal(StudentGroupDTO group) {
    StudentGroup newGroup = new StudentGroup();
    newGroup.setGroupId(group.name);
    newGroup.setSpecialty(group.specialty);
    newGroup.setEmail(group.email);
    newGroup.setStartYear(group.startYear);
    newGroup.setTripTime(group.tripTime);
    newGroup.setFreeTime(group.freeTime);
    newGroup.setYear(group.year);
    return newGroup;
  }
}
