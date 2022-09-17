package com.mate.carpool.persistence;

import com.mate.carpool.model.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findById(String id);

  UserEntity findByEmail(String email);

  Boolean existsByEmail(String email);

  UserEntity findByEmailAndPassword(String email, String password);
}
