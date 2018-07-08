package com.minimall.boilerplate.business.dto;

import lombok.Data;

@Data
public class CommodityDTO {
    private Long id;

    private String userId;// 用户表
    private String type; // 字典表（类型  1.热销 2.新品上架）
    private String typeName;
    private String headline; // 标题，如：商品名
    private String subtitle;// 子标题 如：商品比较详细的描述
    private String previewImg;// 预览图 图片base64
    private String comNo;// 商品No
    private String commodityDetails;// 商品详情 如：淘宝网商品详情描述
    private String originalPrice; // 原价
    private String sellingPrice;// 售价
    private String producedDate; // 生产日期
    private String startGuaPeriodDate;// 保质期 开始
    private String endGuaPeriodDate; // 保质期 结束
    private Integer inventory;// 库存
    private Integer salesVolume;// 销量
    private String manufacturer;// 生产厂家

    // 状态  1.待发布 2.销售中  3.已下架
    private Integer commodityStatus;
    private String commodityStatusName;

    private String soldOutTime;// 下架时间
    private Long updaterId;// 更新者
    private String updaterName; // 更新者名称
    private String updateTime;// 更新时间
    private String[] delIds;
    private String  searchContant;

    private String codeKey;// codeKey
}
