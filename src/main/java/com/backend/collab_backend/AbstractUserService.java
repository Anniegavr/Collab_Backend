package com.backend.collab_backend;

public interface AbstractUserService {
  Long signin(String login, String password, String role);
//  Long signup(String login, String ema)
}
