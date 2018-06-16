package com.minimall.boilerplate.business.dto;

import lombok.Data;


@Data
public class MessagesDTO {
    private Long id;
    private Long userId; // 用户Id
    private String userName; //用户名称

    private Long type; // 模块消息类型 MSG_TYPE
    private String typeName;  // 消息名称

    private String businessId; // 对应业务Id  再不同的type不同下，Id代表的意思也会不同
    private String remarks;// 备注说明
    private String title; // 标题
    private String sendTime;  // 发送时间

    private Integer isRead; // 是否已读  0：未读 1：已读
    private String isReadName;

    private String readTime;  // 已读时间

}
