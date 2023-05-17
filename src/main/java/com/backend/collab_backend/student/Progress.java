package com.backend.collab_backend.student;

public class Progress {
  public Long taskId;

  public Progress(String taskName, int progress) {
    this.taskName = taskName;
    this.progress = progress;
  }

  public String taskName;
  public int progress;
}
