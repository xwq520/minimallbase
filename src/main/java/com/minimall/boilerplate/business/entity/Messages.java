package com.minimall.boilerplate.business.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title: 系统消息表
 * <p>Description: 系统消息表</p>
 *
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_messages_code", columnNames = {"id","deletedAt"})
})
@Data
@Where(clause = "deletedAt=0")
public class Messages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户Id
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId")
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    // 模块消息类型 MSG_TYPE
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "type")
    @NotFound(action= NotFoundAction.IGNORE)
    private Dictionary messageType;

    // 对应业务Id  再不同的type不同下，Id代表的意思也会不同
    private String businessId;
    // 备注说明
    @Column(length = 16777216)
    private String remarks;
    // 标题
    private String title;
    // 发送时间
    private Timestamp sendTime;
    // 是否已读  0：未读 1：已读
    private Integer isRead=0;
    // 已读时间
    private Timestamp readTime;

    // 数据版本
    @Version
    private Integer lastVersion;
    // 数据在何时被删除(时间点)
    private Long deletedAt=0l;
    // 创建者
    private Long creatorId;
    // 创建时间
    private Long createTime;
    // 更新者
    private Long updaterId;
    // 更新时间
    private Long updateTime;

    public void deletedAtNow() {
        this.deletedAt = System.currentTimeMillis();
    }

    public Messages() {
    }

}
