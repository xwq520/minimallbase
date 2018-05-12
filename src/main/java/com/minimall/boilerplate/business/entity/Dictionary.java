package com.minimall.boilerplate.business.entity;

import com.minimall.boilerplate.system.listener.AutoSettingEntityListener;
import lombok.Data;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: 字典表
 * <p>Description: 字典表 </p>
 */
@Entity
@EntityListeners(AutoSettingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_dictionary_id_deletedAt", columnNames = {"code", "deletedAt"})
})
@Where(clause = "deletedAt = 0")
@Data
public class Dictionary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户表
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    private User user;

    //字典code
    private String code;
    //字典名称
    private String name;
    //分类名称
    private String className;
    //备注
    private String remarks;

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

    public Dictionary(){

    }
}
