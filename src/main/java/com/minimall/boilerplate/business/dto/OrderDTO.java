package com.minimall.boilerplate.business.dto;

import lombok.Data;


@Data
public class OrderDTO {

    private Long id;
    private String orderNo;
    private String orderTime; // 下订单时间
    private Long commodityId; // 商品表
    private String commodityNo; // 商品no
    private String commodityName; // 商品name
    private String userId; // 用户表

    private Integer purchaseQuantity;// 购买数量
    private String orderMoney;// 订单金额
    private String address;// 收货地址

    // 订单状态 1.待支付  2.已支付（代发货） 3.已发货  4. 已取消订单
    private Integer orderStatus;
    private String orderStatusName;

    private String playTime;// 支付时间
    private String shipmentsTime;// 发货时间
    private String cancelTime;// 取消订单时间

    private Long updaterId;// 更新者
    private String updaterName; // 更新者名称
    private String updateTime;// 更新时间
    private String searchContant;
}
