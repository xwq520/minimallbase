package com.minimall.boilerplate.common;

import java.util.HashMap;

/**
 * Title: JSON响应对象.
 * <p>Description: 可使用用户消息创建该对象.</p>

 */
public final class MessageObject extends HashMap<String, Object> {

  private static final String CODE_KEY = "code";
  private static final String MESSAGE_KEY = "message";

  private MessageObject() {
  }

  public static MessageObject of(Message message) {
    MessageObject mo = new MessageObject();
    mo.put(CODE_KEY, message.code());
    mo.put(MESSAGE_KEY, message.message());
    return mo;
  }

  @Override
  public MessageObject put(String key, Object value) {
    super.put(key, value);
    return this;
  }

}
