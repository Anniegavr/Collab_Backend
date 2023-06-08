package com.backend.collab_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AbstractUserController {
  private final AbstractUserService abstractUserService;
  @PostMapping("/signin")
  public ResponseEntity<Long> signin(@RequestBody SigninRequest signinRequest) {
    Long id = abstractUserService.signin(signinRequest.login, signinRequest.password, signinRequest.role);
    return ResponseEntity.ok(id);
  }
}
