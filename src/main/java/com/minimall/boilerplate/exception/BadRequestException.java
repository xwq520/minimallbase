package com.minimall.boilerplate.exception;

import com.minimall.boilerplate.common.Message;

/**
 * Title: .
 * <p>Description: </p>

 */
public class BadRequestException extends RequestException {
  public BadRequestException(Message mesaage) {
    super(mesaage);
  }
}
