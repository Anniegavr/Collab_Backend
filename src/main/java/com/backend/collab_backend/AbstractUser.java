package com.backend.collab_backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractUser implements Serializable {

  @SequenceGenerator(
          name="id_sequence",
          sequenceName = "id_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "id_sequence"
  )
  @Id
  private Long id;
  @Column(name="password", nullable = false)
  private String password;

  @Column(name="first_name", nullable = false)
  private String firstName;

  @Column(name="last_name", nullable = false)
  private String lastName;

  @Column(name = "age", nullable = false)
  private Integer age;

  @Column(name="email", nullable = false)
  private String email;

  @Column(name="username", nullable = false, unique = true)
  private String username;
}
