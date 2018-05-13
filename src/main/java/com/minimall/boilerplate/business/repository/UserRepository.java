package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId,String password);
}
