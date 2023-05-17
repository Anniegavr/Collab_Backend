package com.backend.collab_backend.student;

public class StudentDTO {
  public StudentDTO(String firstName, String lastName, String email, String specialty, String year) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.specialty = specialty;
    this.year = year;
  }

  public String firstName;
  public  String lastName;
  public String email;
  public  String specialty;
  public String year;
}
