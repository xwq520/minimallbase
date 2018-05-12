package com.minimall.boilerplate.exception;

import com.minimall.boilerplate.common.Message;

/**
 * Title: .
 * <p>Description: </p>

 */
public class AbstractException extends RuntimeException {

  private Message message;

  public AbstractException() {
    super();
  }

  public AbstractException(String message, Throwable cause) {
    super(message, cause);
  }

  public AbstractException(Message message, Throwable cause) {
    super(message.message(), cause);
    this.message = message;
  }

  public AbstractException(Throwable cause) {
    super(cause);
  }

  public AbstractException(String message) {
    super(message);
  }

  public AbstractException(Message message) {
    super(message.message());
    this.message = message;
  }

  public Message message() {
    return message;
  }
}