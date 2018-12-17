package com.liuxinwen.rocket.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 多线程批处理执行任务服务,使用阻塞队列实现,当任务个数小于{@link TaskExecutorService#DEFAULT_LOWEST_CRITICAL_TASK_NUM}
 * 时只启动一个线程执行任务，大于此值才启动指定线程个数执行任务
 *
 * @author liuxinwen
 */
@Slf4j
@Component
public class TaskExecutorService {
    /**
     * 默认启动线程个数，当被执行的任务数量大于DEFAULT_LOWEST_CRITICAL_TASK_NUM值才起作用
     */
    private static final int DEFAULT_TASK_THREAD_NUM = 2 * Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 默认最低task任务数量，当任务的个数小于此值只启动一个线程
     */
    private static final int DEFAULT_LOWEST_CRITICAL_TASK_NUM = 100;

    @Autowired
    private ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean;

    /**
     * 使用默认配置执行task
     *
     * @param taskName    task名称，无特殊意义，仅仅为了在执行task抛出异常后记录日志使用，建议传入一个有意义的值
     * @param taskService {@link TaskService}
     * @param dataList    待执行任务列表
     * @param <T>
     */
    public <T> void executeTask(String taskName, TaskService<T> taskService, List<T> dataList) {
        executeTask(taskName, taskService, dataList, DEFAULT_TASK_THREAD_NUM);
    }

    /**
     * 使用指定线程数执行task
     *
     * @param taskName      task名称，无特殊意义，仅仅为了在执行task抛出异常后记录日志使用，建议传入一个有意义的值
     * @param taskService   {@link TaskService}
     * @param dataList      待执行任务列表
     * @param taskThreadNum 执行task的线程数 默认为5个
     * @param <T>
     */
    public <T> void executeTask(String taskName, TaskService<T> taskService, List<T> dataList, int taskThreadNum) {
        executeTask(taskName, taskService, dataList, new CommonExecutor<T>(), threadPoolExecutorFactoryBean.getObject(), taskThreadNum);
    }

    /**
     * 执行task核心方法
     *
     * @param taskName        task名称，无特殊意义，仅仅为了在执行task抛出异常后记录日志使用，建议传入一个有意义的值
     * @param taskService     {@link TaskService}
     * @param dataList        待执行任务列表
     * @param taskExecutor    task执行器
     * @param executorService {@link ExecutorService}
     * @param num             执行task的线程数量
     * @param <T>             待执行任务对象
     */
    private <T> void executeTask(String taskName, TaskService<T> taskService, List<T> dataList,
                                 Executor<T> taskExecutor, ExecutorService executorService, int num) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        ArrayBlockingQueue<T> taskList = new ArrayBlockingQueue<>(dataList.size());
        for (T t : dataList) {
            try {
                taskList.put(t);
            } catch (InterruptedException e) {
                log.error("普通list转换为阻塞队列出现异常", e);
                Thread.currentThread().interrupt();
            }
        }
        if (num <= 0 || taskList.size() < DEFAULT_LOWEST_CRITICAL_TASK_NUM) {
            num = 1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 1; i <= num; i++) {
            Task<T> task = new Task<>(taskList + "_TaskThread_" + i, countDownLatch, taskService,
                    taskExecutor, taskList);
            executorService.execute(task);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("executeTask线程被打断", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 真正执行Task的线程，由外部线程池来管理
     *
     * @param <T>
     * @author liuxinwen
     */
    private static class Task<T> implements Runnable {
        private String name;
        private CountDownLatch countDownLatch;
        private Executor<T> taskExecutor;
        private BlockingQueue<T> blockingQueue;
        private TaskService<T> taskService;

        private Task(String name, CountDownLatch countDownLatch, TaskService<T> taskService,
                     Executor<T> taskExecutor, BlockingQueue<T> blockingQueue) {
            this.name = name;
            this.taskExecutor = taskExecutor;
            this.blockingQueue = blockingQueue;
            this.countDownLatch = countDownLatch;
            this.taskService = taskService;
        }

        @Override
        public void run() {
            try {
                taskExecutor.exec(taskService, blockingQueue, name);
            } catch (Exception e) {
                log.error(name + "执行Task发生异常", e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
