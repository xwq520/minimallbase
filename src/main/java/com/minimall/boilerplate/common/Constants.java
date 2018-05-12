package com.minimall.boilerplate.common;

import java.util.HashMap;
import java.util.Map;

import static com.minimall.boilerplate.common.StringHelper.joinWith;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Title: 常量类.
 * <p>Description: 定义公用常量.</p>

 */
public final class Constants {


  // Http请求头部 验证信息Key
  public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
  // Http请求头部 验证信息Schema
  public static final String AUTHORIZATION_SCHEMA_BEARER = "Bearer";
  // Http请求头部 当前页面ID
  public static final String HTTP_HEADER_PAGE_ID = "x-page-id";
  // Http请求头部 分页页数
  public static final String HTTP_HEADER_PAGINATION_INDEX = "x-pagination-index";
  // Http请求头部 分页每页数据条数
  public static final String HTTP_HEADER_PAGINATION_SIZE = "x-pagination-size";
  // Http请求头部 公司ID
  public static final String HTTP_HEADER_COMPANY_ID = "x-company-id";

  // Http请求头部 微信ID
  public static final String HTTP_HEADER_WECHAT_ID = "x-wechat-id";
  // Http请求头部 第三方平台类型
  public static final String HTTP_HEADER_NICK_NAME = "x-wechat-name";
  // Http请求头部 第三方平台类型
  public static final String HTTP_HEADER_HEADIMGURL= "x-wechat-img";
  // Http请求头部 人员id
  public static final String HTTP_HEADER_EMPLOYEE_ID = "x-employee-id";
  // Http请求头部 第三方ID
  public static final String HTTP_HEADER_THIRD_ID = "x-third-id";
  // Http请求头部 第三方平台类型
  public static final String HTTP_HEADER_THIRD_PLAT_TYPE = "x-plat-type";
  // Http请求头部 公司秘钥
  public static final String HTTP_HEADER_COMPANY_KEY = "x-company-key";
  // Http请求头部 用户ID
  public static final String HTTP_HEADER_USER_ID = "x-user-id";
  // Http请求头部 用户角色ID
  public static final String HTTP_HEADER_USER_ROLE_ID = "x-user-role-id";
  // Http请求头部 JSON_UTF8类型缩写
  public static final String JSON_UTF8 = APPLICATION_JSON_UTF8_VALUE;

  // 导出文件后缀
  public static final String EXPORT_FILE_POSTFIX = "xlsx";

  // Token密钥
  public static final String SECRET = "i4]89FAp&EV]Li2/#h4Ls4yZq;.AdPu8gh*h363Qw@tBK)pLaC";
  // Token签发方
  public static final String ISSUER_REFERENCE = "fusion.aeonfantasy.com";
  // Token有效期
  public static final long EXPIRATION_DAY = 5L;

  // 系统保留角色
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  // 用于显示用户表中不存在的更新者(userId = 0)
  public static final String SYSTEM_USERNAME = "系统";
  // 用于显示用户表中不存在的更新者(userId < 0或null)
  public static final String UNKNOWN_USERNAME = "未知用户";

  // Redis
  public static final String REDIS_SEPARATOR = ":";
  public static final String REDIS_PREFIX = "fusion";

  private Constants() {
  }

  // User 性别
  public static final Integer SEX_00 = 0;
  public static final String SEX_00_TXT = "女";
  public static final Integer SEX_01 = 1;
  public static final String SEX_01_TXT = "男";

  // Commodity 状态  1.待发布 2.销售中  3.已下架
  public static final Integer COMMODITY_STATUS_00 = 0;
  public static final String  COMMODITY_STATUS_00_TXT = "待发布";
  public static final Integer COMMODITY_STATUS_01 = 1;
  public static final String  COMMODITY_STATUS_01_TXT = "销售中";
  public static final Integer COMMODITY_STATUS_02 = 1;
  public static final String  COMMODITY_STATUS_02_TXT = "已下架";

}
