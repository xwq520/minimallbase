package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserId(String userId);
    Optional<User> findByCodeKey(String codeKey);

    @Query(value = " select * from  mi_user where (userId = ?1 or userPhone = ?2) and deletedAt = 0",nativeQuery = true)
    Optional<User> findByUserInfo(String userId,String userPhone);

    Optional<User> findByUserIdAndPassword(String userId,String password);
}
