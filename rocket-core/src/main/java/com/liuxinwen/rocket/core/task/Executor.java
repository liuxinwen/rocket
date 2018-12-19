package com.liuxinwen.rocket.core.task;

import java.util.concurrent.BlockingQueue;

/**
 * 任务执行器接口
 * @see CommonExecutor
 * @author liuxinwen
 * @param <T>
 *
 * @date 2017年6月22日
 */
interface Executor<T> {

	/**
	 * 任务执行接口
	 * @param taskService 实现了{@link TaskService}接口的实现类
	 * @param blockingQueue 阻塞队列
	 * @param threadName 线程名称(可选，仅仅为了记录日志时使用)
	 */
	void exec(TaskService<T> taskService, BlockingQueue<T> blockingQueue, String threadName);
}
