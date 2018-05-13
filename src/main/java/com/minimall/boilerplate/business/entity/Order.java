package com.minimall.boilerplate.business.entity;

import com.minimall.boilerplate.system.listener.AutoSettingEntityListener;
import lombok.Data;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title: 订单表
 * <p>Description: 订单实体</p>
 */

@Entity
@EntityListeners(AutoSettingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_order_id_deletedAt", columnNames = {"id", "deletedAt"})
})
@Where(clause = "deletedAt = 0")
@Data
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    // 下订单时间
    private Timestamp orderTime;

    // 商品表
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "commodityId")
    private Commodity commodity;

    // 用户表
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId", referencedColumnName="userId")
    private User user;

    // 购买数量
    private Integer purchaseQuantity;
    // 订单金额
    private Float orderMoney;
    // 收货地址
    private String address;
    // 订单状态 1.待支付  2.已支付（代发货） 3.已发货  4. 已取消订单
    private int orderStatus;
    // 支付时间
    private Timestamp playTime;
    // 发货时间
    private Timestamp shipmentsTime;
    // 取消订单时间
    private Timestamp cancelTime;

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

    public Order() {
    }

}