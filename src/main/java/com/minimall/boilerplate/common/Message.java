package com.minimall.boilerplate.common;

/**
 * Title: 用户消息.
 * <p>Description: 存放返回给用户的消息.</p>

 */
public enum Message {

  I101("101", "登录成功"),
  I102("102", "操作成功"),
  I103("103", "数据同步中"),
  I104("104", "所有U8数据均已同步"),
  I105("105", "有未关联的U8部门"),
  I106("106", "有未关联的U8科目"),
  I107("107", "有不符合U8科目和部门关联关系的凭证"),
  //微信注册消息
  I108("108", "注册成功"),

  E100("-100", "系统错误, 请联系管理员"),
  E101("-101", "用户未登录, 请登录后重试"),
  E102("-102", "保存的登录信息已过期, 请重新登录"),
  E103("-103", "您的账号尚未启用, 请联系管理员"),
  E104("-104", "登录失败! 请检查您的用户名和密码是否正确"),
  E105("-105", "操作失败, 请稍后再试或联系供应商寻求帮助"),
  E106("-106", "您没有权限访问该链接"),
  E107("-107", "获取用户列表失败, 请重试"),
  E108("-108", "您的登录信息已失效, 请重新登录"),
  E109("-109", "抱歉! 您查找的内容不存在"),
  E110("-110", "保存失败! 请检查您提交的内容是否有效"),
  E111("-111", "请求内容验证失败"),
  E112("-112", "删除失败! 该内容不存在"),
  E113("-113", "修改密码失败! 请检查您提交的内容是否有效"),
  E114("-114", "原密码不能为空"),
  E115("-115", "新密码不能为空"),
  E116("-116", "确认密码不能为空"),
  E117("-117", "两次输入不一致, 请重新输入"),
  E118("-118", "原密码输入错误, 请重新输入"),
  E119("-119", "U8数据同步检查失败"),
  E120("-120", "U8数据同步失败"),
  E121("-121", "未提供有效的日期信息"),
  E122("-122", "该角色已存在, 不允许重复创建"),
  E123("-123", "该用户已存在, 不允许重复创建"),
  E124("-124", "处理失败"),
  E125("-125", "审核流程处理失败"),
  E126("-126", "存在当前预算年度以前的数据, 不能删除"),
  E127("-127", "所选店铺未关联U8部门, 无法获取PL实绩数据"),
  E128("-128", "文件导入失败。"),
  E129("-129", "保存失败! 您提交的内容已存在"),
  E130("-130", "您的账号已到期, 请联系管理员"),
  E131("-131", "不允许删除系统角色"),
  E132("-132", "导出条件费用项目必须只能选择一个"),
  E133("-133", "导出条件科目必须只能选择一个"),
  E134("-134", "未找到店铺租金预算的编制部门，请联系供应商重新设置"),
  E135("-135", "未找到新店模型预算的编制部门，请联系供应商重新设置"),
  E136("-136", "未找到编制部门，请联系供应商重新设置"),
  E137("-137", "文件导入失败。请检查导入模板和当前设置的导入条件是否匹配！"),
  E138("-138", "没有查询到员工考勤打卡数据"),
  E139("-139", "第三方平台ID错误"),
  E140("-140", "参数错误，非预期参数"),
  E141("-141", "申请时间有重复"),
  E142("-142", "未找到数据"),
  E143("-143", "加班时间不能跨天"),
  E144("-144", "请假时间不能跨月"),
  E145("-144", "找不到盘点数据"),
  E146("-144", "申请时间不在当月考勤范围内"),

  //用户信息
  E160("-160", "该用户账号已过期,请联系管理员"),
  E161("-161", "该用户账号已被锁定,请联系管理员");
  private String code;
  private String msg;

  Message(String code, String message) {
    this.code = code;
    this.msg = message;
  }

  public String code() {
    return code;
  }

  public String message() {
    return msg;
  }

  @Override
  public String toString() {
    return "Message{" +
        "code='" + code + '\'' +
        ", msg='" + msg + '\'' +
        '}';
  }
}