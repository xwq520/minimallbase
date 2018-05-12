package com.minimall.boilerplate.common;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Title: .
 * <p>Description: </p>

 */
@Component
public class TaskExecutorFactory {
  public final TaskExecutor executor;

  public TaskExecutorFactory() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    int cores = Runtime.getRuntime().availableProcessors();
    executor.setCorePoolSize(0);
    executor.setMaxPoolSize(2*cores);
    // 使用直接提交队列
    executor.setQueueCapacity(0);
    // 设置线程池满载时在主线程执行多余任务的策略
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.afterPropertiesSet();
    this.executor = executor;
  }
}
