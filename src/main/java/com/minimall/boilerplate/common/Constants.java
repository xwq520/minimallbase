package com.minimall.boilerplate.common;

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


  // Http请求头部 JSON_UTF8类型缩写
  public static final String JSON_UTF8 = APPLICATION_JSON_UTF8_VALUE;

  // http请求头部 userId 用户id
  public static final String HTTP_USER_ID = "x-user-id";


  private Constants() {
  }

  // User 性别
  public static final int SEX_00 = 0;
  public static final String SEX_00_TXT = "女";
  public static final int SEX_01 = 1;
  public static final String SEX_01_TXT = "男";

  // is lock
  public static final int LOCK_00 = 0;
  public static final String LOCK_00_TXT = "否";
  public static final int LOCK_01 = 1;
  public static final String LOCK_01_TXT = "是";

  // Commodity 状态  1.待发布 2.销售中  3.已下架
  public static final int COMMODITY_STATUS_00 = 0;
  public static final String  COMMODITY_STATUS_00_TXT = "待发布";
  public static final int COMMODITY_STATUS_01 = 1;
  public static final String  COMMODITY_STATUS_01_TXT = "销售中";
  public static final int COMMODITY_STATUS_02 = 2;
  public static final String  COMMODITY_STATUS_02_TXT = "已下架";


  //  Order 订单状态 1.待支付  2.已支付（代发货） 3.已发货  4. 已取消订单
  public static final int ORDER_STATUS_01 = 1;
  public static final String  ORDER_STATUS_01_TXT = "待支付";
  public static final int ORDER_STATUS_02 = 2;
  public static final String  ORDER_STATUS_02_TXT = "已支付";
  public static final int ORDER_STATUS_03 = 3;
  public static final String  ORDER_STATUS_03_TXT = "已发货";
  public static final int ORDER_STATUS_04 = 4;
  public static final String  ORDER_STATUS_04_TXT = "已取消订单";

  // 消息 0：未读 1：已读
  public static final int MESSAGES_00 = 0;
  public static final String  MESSAGES_00_TXT = "未读";
  public static final int MESSAGES_01 = 1;
  public static final String  MESSAGES_01_TXT = "已读";

}
