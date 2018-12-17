package com.liuxinwen.rocket.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

/**
 * 通用配置类
 *
 * @author liuxinwen
 * @date 2017年6月22日
 */
@Configuration
public class Config {

    private static final int CORE_POOL_SIZE = Constant.CORE_POOL_SIZE;
    private static final int MAX_POOL_SIZE = Constant.MAX_POOL_SIZE;
    private static final int QUEUE_CAPACITY = Constant.QUEUE_CAPACITY;
    private static final int KEEP_ALIVE_SECOND = Constant.KEEP_ALIVE_SECOND;


    @Bean
    public ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean() {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setCorePoolSize(CORE_POOL_SIZE);
        factoryBean.setMaxPoolSize(MAX_POOL_SIZE);
        factoryBean.setQueueCapacity(QUEUE_CAPACITY);
        factoryBean.setKeepAliveSeconds(KEEP_ALIVE_SECOND);
        factoryBean.setThreadNamePrefix("task-threadPoolExecutor-");
        return factoryBean;
    }
}
