package com.mate.carpool.service;

import com.mate.carpool.model.UserEntity;
import com.mate.carpool.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserEntity create(final UserEntity userEntity) {
    if (null == userEntity || null == userEntity.getEmail()) {
      throw new RuntimeException("Invalid arguments");
    }

    final String email = userEntity.getEmail();
    if (userRepository.existsByEmail(email)) {
      log.warn("Email already exists {}", email);
      throw new RuntimeException("Email already exists");
    }

    return userRepository.save(userEntity);
  }

  public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
    final UserEntity originalUser = userRepository.findByEmail(email);

    if (null != originalUser && encoder.matches(password, originalUser.getPassword())) {
      return originalUser;
    }
    return null;
  }
}
