package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessagesRepository extends JpaRepository<Messages, Long>, JpaSpecificationExecutor<Messages> {

    // 更新是否读取状态
    @Modifying
    @Query(value = " update mi_messages set isRead=1,readTime=NOW() where deletedAt=0 and isRead=0 and id=?1",nativeQuery = true)
    Integer updateIsRead(Long messageId);

    Integer countByUserIdAndIsRead(Long userId, Integer isRead);

    //Integer countByUserId(Long userId);

}
