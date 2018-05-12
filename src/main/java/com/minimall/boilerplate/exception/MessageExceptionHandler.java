package com.minimall.boilerplate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * Title: .
 * <p>Description: .</p>

 */
public class MessageExceptionHandler implements ErrorHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void handleError(Throwable e) {
    logger.error("消息处理异常. {}", e.getMessage(), e);
  }
}
