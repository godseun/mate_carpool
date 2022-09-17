package com.mate.carpool.service;

import com.mate.carpool.model.UserEntity;
import com.mate.carpool.persistence.UserRepository;

import java.util.Optional;

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
      log.debug("UserService.create Email already exists {}", email);
      return userRepository.findByEmail(email);
    }
    return userRepository.save(userEntity);
  }

  public Optional<UserEntity> getMyInfo(final String id) {
    return userRepository.findById(id);
  }

  public Optional<UserEntity> update(final String id, final UserEntity userEntity) {
    System.out.println(userEntity.toString());
    Optional<UserEntity> original = userRepository.findById(id);

    original.get().setStudentNo(userEntity.getStudentNo());
    original.get().setDeptNo(userEntity.getDeptNo());
    original.get().setUserType(userEntity.getUserType());

    userRepository.save(original.get());

    return getMyInfo(id);
  }

  public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
    final UserEntity originalUser = userRepository.findByEmail(email);

    if (null != originalUser && encoder.matches(password, originalUser.getPassword())) {
      return originalUser;
    }
    return null;
  }
}
