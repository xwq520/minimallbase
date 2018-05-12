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

  // 薪资验证信息Key
  public static final String MONEY_KEY = "jwchatMoneyKey";
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
  //第三方平台类型: 微信
  public static final Integer JX_THIRD_PLAT_TYPE_WECHAT = 1;
  public static final String JX_THIRD_PLAT_TYPE_STR_WECHAT = "wechat";
  //第三方平台类型: 钉钉
  public static final Integer JX_THIRD_PLAT_TYPE_DINGTALK = 2;
  public static final String JX_THIRD_PLAT_TYPE_STR_DINGTALK = "dingTalk";
  //第三方平台ID类型
  public static final Integer JX_THIRD_ID_TYPE_OPEN = 1; //openid
  public static final Integer JX_THIRD_ID_TYPE_UNION = 2; //unionid
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
  public static final String REDIS_USERS_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "users", "");
  public static final String REDIS_DEPARTMENTS_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "departments", "");
  public static final String REDIS_BUDGET_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "budget", "");
  public static final String REDIS_ACTUAL_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "actual", "");
  public static final String REDIS_SYSTEM_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "system", "");
  // 用于保存U8数据同步状态的Redis Key
  public static final String REDIS_U8_PREFIX = StringHelper.joinWith(REDIS_SEPARATOR, REDIS_PREFIX, "u8", "");

  public static final String ANALYSIS_PL_MAKE = "ANALYSIS_PL_MAKE";

  public static final String FILENAME_ATTENDANCE = "attendence";
  public static final String FILENAME_ATTENDANCE_APPLY = "attendenceApply";
  public static final String FILENAME_DAILYREPORT="dailyreport";
  //打卡类型
  public static final String ATTENDENCE_TYPE_OFF="下班打卡";
  public static final String ATTENDENCE_TYPE_ON="上班打卡";
  public static final String ATTENDENCE_TYPE_HALFWAY="中途打卡";
  public static final Map<Integer, String> ATTENDENCE_TYPE_MAP = new HashMap<>();
  //日考勤范围
  public static final String ATT_RANGE_START="startDate";
  public static final String ATT_RANGE_END="endDate";
  //月考勤范围
  public static final String MONTH_RANGE_START="startMonth";
  public static final String MONTH_RANGE_END="endMonth";
  //考勤就基础数据
  //正常考勤范围
  public static final String ATTENDENCE_ATTENDANCE_H_WORK="H_WORK";
  //深夜加班范围
  public static final String ATTENDENCE_ATTENDANCE_OV_TIME="OV_TIME";
  //上下班考勤时点
  public static final String ATTENDENCE_ATTENDANCE_WORK_T="WORK_T";
  // 每月模板生成开始日
  public static final String ATTENDENCE_ATTENDANCE_RE_WODAY="RE_WODAY";
  // 每月模板生成开始日
  public static final String ATTENDENCE_ATTENDANCE_SUP_COUNT="SUP_COUNT";
  //填写申述大分类className
  public static final String TRAVEL_TYPE="TRAVEL_TYPE";//出差
  public static final String ABNORMAL_TYPE="ABNORMAL_TYPE";//补卡
  public static final String APPLY_OVERTIME="APPLY_OVERTIME";//加班
  public static final String APPLY_LEAVE="APPLY_LEAVE";//请假
  public static final String APPLY_HOUR="APPLY_HOUR";//申诉

  //操作流code
  public static final String OPT_SAVE="OPT_SAVE";//保存
  public static final String OPT_SUB="OPT_SUB";//提交
  public static final String OPT_AGE="OPT_AGE";//同意
  public static final String OPT_REF="OPT_REF";//拒绝
  public static final String OPT_RET="OPT_RET";//撤回
  //打卡code
  public static final String A_ON="A_ON";//上班
  public static final String A_OFF="A_OFF";//下班
  public static final String A_IN="A_IN";//进店
  public static final String A_OUT="A_OUT";//出店
  //public static final String A_ABN="A_ABN";//补卡
  // 维修端签名key
  public static final String TOKEN_SIGN_KEY = "WECHAT";

  // 工时计算规则获取
  public static final String ATTENDENCE_ATTENDANCE_EXCE_NUM="EXCE_NUM";
  //权限检索check标志
  public static final String AUTHORITY_TYPE = "rule";

  //考勤月默认规则
  public static  final String MONTH_RULE="1,1";
  //考勤日默认规则
  public static  final String DAY_RULE="00:00:00,00:00:00";
  static {
    ATTENDENCE_TYPE_MAP.put(0, ATTENDENCE_TYPE_OFF);
    ATTENDENCE_TYPE_MAP.put(1, ATTENDENCE_TYPE_ON);
    ATTENDENCE_TYPE_MAP.put(2, ATTENDENCE_TYPE_HALFWAY);
  }

  private Constants() {
  }

  // 报修者状态：2.等待维修，4.需继续维修，5.需返厂维修，9.完成
  // 维修者状态：6.维修完成

  // 维修状态:1：已确认（分配维修者=》等待维修）2：等待维修 3：维修中 4：需继续维修 5:需返厂维修 6：待确认 9：完成
  public static final int ASSETSREPAIR_STATUS_01 = 1;
  public static final String ASSETSREPAIR_STATUS_01_TXT = "已确认";
  public static final int ASSETSREPAIR_STATUS_02 = 2;
  public static final String ASSETSREPAIR_STATUS_02_TXT = "等待维修";
  public static final int ASSETSREPAIR_STATUS_03 = 3;
  public static final String ASSETSREPAIR_STATUS_03_TXT = "维修中";
  public static final int ASSETSREPAIR_STATUS_04 = 4;
  public static final String ASSETSREPAIR_STATUS_04_TXT = "需继续维修";
  public static final int ASSETSREPAIR_STATUS_05 = 5;
  public static final String ASSETSREPAIR_STATUS_05_TXT = "需返厂维修";
  public static final int ASSETSREPAIR_STATUS_06 = 6;
  public static final String ASSETSREPAIR_STATUS_06_TXT = "待确认";
  public static final int ASSETSREPAIR_STATUS_09 = 9;
  public static final String ASSETSREPAIR_STATUS_09_TXT = "完成";

  // 资产状态: 0：可用，1：使用中, 2：新入库, 9：已报废
  public static final int ASSETSBASE_STATUS_00 = 0;
  public static final String ASSETSBASE_STATUS_00_TXT = "可用";
  public static final int ASSETSBASE_STATUS_01 = 1;
  public static final String ASSETSBASE_STATUS_01_TXT = "使用中";
  public static final int ASSETSBASE_STATUS_02 = 2;
  public static final String ASSETSBASE_STATUS_02_TXT = "新入库";
  public static final int ASSETSBASE_STATUS_09 = 9;
  public static final String ASSETSBASE_STATUS_09_TXT = "已报废";

  // 维修类别  0:资产自修 1:申请报修
  public static final int ASSETSREPAIR_TYPE_00 = 0;
  public static final String ASSETSREPAIR_TYPE_00_TXT = "资产自修";
  public static final int ASSETSREPAIR_TYPE_01 = 1;
  public static final String ASSETSREPAIR_TYPE_01_TXT = "申请报修";


  // 转移单状态：  3：待转出（已审核完成） 4：转移中（待转入）6：取消转出 7：取消转入 9：完成
  public static final String ASSETTRANS_STATUS_03 = "3";
  public static final String ASSETTRANS_STATUS_03_TXT = "待转出";
  public static final String ASSETTRANS_STATUS_04 = "4";
  public static final String ASSETTRANS_STATUS_04_TXT = "转移中";
  public static final String ASSETTRANS_STATUS_06 = "6";
  public static final String ASSETTRANS_STATUS_06_TXT = "取消转出";
  public static final String ASSETTRANS_STATUS_07 = "7";
  public static final String ASSETTRANS_STATUS_07_TXT = "取消转入";
  public static final String ASSETTRANS_STATUS_09 = "9";
  public static final String ASSETTRANS_STATUS_09_TXT = "完成";


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
