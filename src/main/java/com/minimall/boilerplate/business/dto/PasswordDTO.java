package com.minimall.boilerplate.business.dto;

import lombok.Data;

/**
 * 修改密码
 */

@Data
public class PasswordDTO {

    private String userId;   // 用户Id
    private String password; // 原密码
    private String newPassword;  // 新密码

}
