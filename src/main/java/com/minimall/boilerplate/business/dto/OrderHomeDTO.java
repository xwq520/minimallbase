package com.minimall.boilerplate.business.dto;

import lombok.Data;


@Data
public class OrderHomeDTO {

    private String countNum;// 销量
    private String commodityNo; // 商品no
    private String commodityName; // 商品name
    private String sumCount="0";// 总销量
    private String sumMoney="0.00";// 总金额
    private String waritCount="0";// 待处理数量


}
