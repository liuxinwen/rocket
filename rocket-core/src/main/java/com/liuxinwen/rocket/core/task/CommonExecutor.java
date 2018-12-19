package com.liuxinwen.rocket.core.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

/**
 * 任务通用执行器,默认{@link Executor}接口实现
 *
 * @author liuxinwen
 * @date 2017年6月23日
 */
@Slf4j
class CommonExecutor<T> implements Executor<T> {
    @Override
    public void exec(TaskService<T> taskService, BlockingQueue<T> blockingQueue, String threadName) {
        while (!blockingQueue.isEmpty()) {
            T t;
            try {
                t = blockingQueue.poll();
                if (t != null) {
                    if (log.isDebugEnabled()) {
                        log.debug(threadName + " taskService: {} execTask parameter: {}", taskService.getClass().getName(), t);
                    }
                    taskService.execTask(t);
                }
            } catch (Exception e) {
                log.error(threadName + " CommonExecutor.exec出现异常异常", e);
            }
        }
    }

}
