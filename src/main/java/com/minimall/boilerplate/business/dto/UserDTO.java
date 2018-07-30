package com.minimall.boilerplate.business.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String userName;// 用户名
    private String userPhone;// 手机号
    private Integer userSex;// 性别 0:女 1：男
    private String userSexName;
    private String userId; // 用户Id
    private String userIds[]; // 用户Ids

    private Integer isLock;// 是否锁住
    private String lockTxt;// 是否锁住
    private String password;// 登录密码

    private String securityCode;// 验证码 ER443 34333
    private String registerTime;// 注册时间
    private String lastTime;// 最后登录时间

    private Long updaterId;// 更新者
    private String updaterName; // 更新者名称
    private String updateTime;// 更新时间
    private String searchContant;// 模糊查询内容
    private String codeKey;

    private String play1;//默认微信支付
    private String play2;//默认支付宝支付
    private String other;
    private String remarks;
    private String expireTime;

}
