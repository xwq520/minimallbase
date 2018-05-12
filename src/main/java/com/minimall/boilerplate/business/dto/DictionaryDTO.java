package com.minimall.boilerplate.business.dto;

import lombok.Data;

@Data
public class DictionaryDTO {
    private  Long id;
    private  Long userId;

    private String code;//字典code
    private String name; //字典名称
    private String className;//分类名称
    private String remarks;//备注

    private Long updaterId;// 更新者
    private String updaterName; // 更新者名称
    private String updateTime;// 更新时间

}
