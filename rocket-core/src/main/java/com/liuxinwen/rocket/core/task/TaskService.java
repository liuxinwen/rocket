package com.liuxinwen.rocket.core.task;

/**
 * 任务执行服务接口，子类实现此接口执行具体的业务逻辑
 *
 * @author liuxinwen
 * @date 2017年6月23日
 */
public interface TaskService<T> {

    /**
     * 根据参数T执行task
     *
     * @param t
     */
    void execTask(T t);

}
