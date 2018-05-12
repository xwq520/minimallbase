package com.minimall.boilerplate.exception;

import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.Message;

/**
 * Title: 验证错误类.
 * <p>Description: 通过{@link Message}创建该类</p>

 */
public class RequestException extends AbstractException {

  public RequestException(Message mesaage) {
    super(mesaage);
  }
}
