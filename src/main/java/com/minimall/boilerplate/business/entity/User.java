package com.minimall.boilerplate.business.entity;

import com.minimall.boilerplate.system.listener.AutoSettingEntityListener;
import lombok.Data;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title: 用户表
 * <p>Description: 用户实体</p>
 */
@Entity
@EntityListeners(AutoSettingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_userId_deletedAt", columnNames = {"userId","userPhone", "deletedAt"})
})
@Where(clause = "deletedAt = 0")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    // 用户名
    private String userName;
    // 手机号
    private String userPhone;
    // 性别 0:女 1：男
    private Integer userSex;
    // 用户Id
    private String userId;
    // 登录密码
    private String password;
    // 是否锁定
    private Integer isLock = 0;
    // 验证码 ER443 34333
    private String securityCode;
    // 注册时间
    private Timestamp registerTime;
    // 最后登录时间
    private Timestamp lastTime;
    // 微信端访问的key
    private String codeKey;
    // 默认微信支付
    private String play1;
    // 默认支付宝支付
    private String play2;
    private String other;
    private String remarks;

    // 到期日 expire
    private Timestamp expireTime;

    // 用户角色表
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userRoleId")
    private UserRole userRole;

    // 数据版本
    @Version
    private Integer lastVersion;
    // 数据在何时被删除(时间点)
    private Long deletedAt = 0L;
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

    public User() {
    }
}
