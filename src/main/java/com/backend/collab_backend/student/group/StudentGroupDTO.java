package com.backend.collab_backend.student.group;

public class StudentGroupDTO {
  public String name;
  public String email;
  public String specialty;
  public int startYear;
  public int tripTime;
  public int freeTime;
  public int sleepTime;
  public int classTime;
  public int year;

  public StudentGroupDTO(String name,
                         String email,
                         String specialty,
                         int startYear,
                         int tripTime,
                         int freeTime,
                         int sleepTime,
                         int classTime,
                         int year) {
    this.name = name;
    this.email = email;
    this.specialty = specialty;
    this.startYear = startYear;
    this.tripTime = tripTime;
    this.freeTime = freeTime;
    this.sleepTime = sleepTime;
    this.classTime =classTime;
    this.year = year;
  }
}
