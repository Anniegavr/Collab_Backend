package com.backend.collab_backend.student.progress;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
  private static final Logger logger = LoggerFactory.getLogger(ProgressServiceImpl.class);
  private final ProgressRepository progressRepository;

  public List<ProgressDTO> findAllForStudent(Long id) {
    List<Progress> progresses = progressRepository.findAllByStudentId(id);
    logger.info("Found [{}] progress records for student_id[{}]", progresses.size(), id);
    List<ProgressDTO> progressDTOS = new ArrayList<>();
    for (Progress progress : progresses) {
      progressDTOS.add(new ProgressDTO(progress.getTaskName(), progress.getTaskProgress()));
    }
    return progressDTOS;
  }

  public ProgressDTO recordProgressForStudent(Long id, ProgressDTO progress) {
    return null;
  }
}
