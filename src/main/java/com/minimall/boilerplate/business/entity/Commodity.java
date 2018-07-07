package com.minimall.boilerplate.business.entity;

import com.minimall.boilerplate.system.listener.AutoSettingEntityListener;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title: 商品表
 * <p>Description: 商品实体</p>
 */
@Entity
@EntityListeners(AutoSettingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_commodity_id_deletedAt", columnNames = {"id", "deletedAt"})
},indexes = {
        @Index(columnList = "userId",name = "userId")
})
@Where(clause = "deletedAt = 0")
@Data

public class Commodity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    // 用户表
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    // 字典表（类型  1.热销 2.新品上架）
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "type")
    private Dictionary dictionary;

    // 商品名No
    private String comNo;
    // 标题，如：商品名
    private String headline;
    // 子标题 如：商品比较详细的描述
    private String subtitle;
    // 预览图 图片base64
    @Column(length = 16777216)
    private String previewImg;
    // 商品详情 如：淘宝网商品详情描述
    @Column(length = 16777216)
    private String commodityDetails;
    // 原价
    private Double originalPrice;
    // 售价
    private Double sellingPrice;
    // 生产日期
    private Timestamp producedDate;
    // 保质期 开始
    private Timestamp startGuaPeriodDate;
    // 保质期 结束
    private Timestamp endGuaPeriodDate;
    // 库存
    private Integer inventory;
    // 销量
    private Integer salesVolume;
    // 生产厂家
    private String manufacturer;
    // 状态  1.待发布 2.销售中  3.已下架
    private int commodityStatus;
    // 下架时间
    private Timestamp soldOutTime;

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

    public Commodity() {
    }
}
