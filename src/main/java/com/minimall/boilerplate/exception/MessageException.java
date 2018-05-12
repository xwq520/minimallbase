package com.minimall.boilerplate.exception;

import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.Message;

/**
 * Title: 验证错误类.
 * <p>Description: 通过{@link Message}创建该类</p>

 */
public class MessageException extends AbstractException {

  private String destination;

  public MessageException(String destination, Message message, Throwable cause) {
    super(message, cause);
    this.destination = destination;
  }

  public String getDestination() {
    return destination;
  }
}
