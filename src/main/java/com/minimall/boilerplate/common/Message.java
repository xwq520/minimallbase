package com.minimall.boilerplate.common;

/**
 * Title: 用户消息.
 * <p>Description: 存放返回给用户的消息.</p>

 */
public enum Message {

  I101("101", "登录成功"),
  I102("102", "操作成功"),

  E105("-105", "操作失败, 请稍后再试或联系供应商寻求帮助"),

  E109("-109", "抱歉! 您查找的内容不存在"),
  E110("-110", "保存失败! 请检查您提交的内容是否有效或已存在"),
  E111("-111", "请求内容验证失败"),

  E124("-124", "处理失败"),

  E140("-140", "参数错误，非预期参数"),

  E162("-162", "登录失败");

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
