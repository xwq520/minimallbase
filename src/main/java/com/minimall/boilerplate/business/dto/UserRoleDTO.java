package com.minimall.boilerplate.business.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Long id;
    private Long userId;// 用户表
    private String role;
    private String name;

    private Long updaterId;// 更新者
    private String updaterName; // 更新者名称
    private String updateTime;// 更新时间
}
