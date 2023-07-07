package com.backend.collab_backend.assignment.to.schedule.assignment.set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentSetServiceImpl implements AssignmentSetService{
  private final AssignmentSetRepository assignmentSetRepository;
  @Override
  public AssignmentSet findBySetId(Long setId) {
    Optional<AssignmentSet> assignmentSetOptional = assignmentSetRepository.findAssignmentSetByAssignmentSetId(setId);
    return assignmentSetOptional.orElseGet(AssignmentSet::new);
  }

  public List<AssignmentSet> findAllSetsBySetId(Long id) {
    return assignmentSetRepository.findAllByAssignmentSetId(id);
  }

  public AssignmentSet findByAssignmentId(Long id) {
    Optional<AssignmentSet> assignmentSet = assignmentSetRepository.findAssignmentSetByAssignmentId(id);
    return assignmentSet.orElse(null);

  }

  @Override
  public AssignmentSet addSet(AssignmentSet assignmentSet) {
    return assignmentSetRepository.save(assignmentSet);
  }

  @Override
  public void deleteSetBySetId(Long setId) {
    Optional<AssignmentSet> assignmentSetOptional = assignmentSetRepository.findAssignmentSetByAssignmentSetId(setId);
    assignmentSetOptional.ifPresent(assignmentSetRepository::delete);
  }
}
