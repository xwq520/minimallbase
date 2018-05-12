package com.minimall.boilerplate.exception;

import com.minimall.boilerplate.common.MessageObject;
import com.minimall.boilerplate.common.Message;
import com.minimall.boilerplate.common.MessageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.minimall.boilerplate.common.Message.E105;
import static com.minimall.boilerplate.common.Message.E111;
import static org.springframework.http.HttpStatus.*;
import static com.minimall.boilerplate.common.MessageObject.of;

/**
 * Title: 错误处理controller.
 * <p>Description: 用于集中处理错误</p>

 */
@ControllerAdvice
@RestController
public class RequestExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public MessageObject notValidRequestExceptionHandler(MethodArgumentNotValidException e)
  {
    logger.warn("验证错误: error={}", e.getMessage());
    BindingResult bindingResult = e.getBindingResult();
    List<String> errors = bindingResult.getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());
    return MessageObject.of(Message.E111).put("errors", errors);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public MessageObject badRequestExceptionHandler(BadRequestException e)
  {
    logger.warn("请求错误: error={}", e.getMessage());
    return MessageObject.of(e.message());
  }

  @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public MessageObject httpMediaTypeNotSupportedExceptionHandler(Exception e)
  {
    return exceptionHandler(e);
  }

  @ResponseStatus(METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public MessageObject httpRequestMethodNotSupportedExceptionHandler(Exception e)
  {
    return exceptionHandler(e);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NotFoundRequestException.class)
  public MessageObject requestExceptionHandler(Exception e)
  {
    logger.warn("未找到资源: error={}", e.getMessage());
    return MessageObject.of(((NotFoundRequestException)e).message());
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler
  public MessageObject exceptionHandler(Exception e)
  {
    logger.error("发生异常: error={}", e.getMessage(), e);
    return MessageObject.of(Message.E105);
  }
}
