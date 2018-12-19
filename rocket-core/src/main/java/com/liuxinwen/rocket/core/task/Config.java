package com.liuxinwen.rocket.core.task;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${thread_pool.core_pool_size:5}")
    private int corePoolSize;
    @Value("${thread_pool.max_pool_size:20}")
    private int maxPoolSize;
    @Value("${thread_pool.queue_capacity:50}")
    private int queueCapacity;
    @Value("${thread_pool.keep_alive_second:60}")
    private int keepAliveSecond;

    @Bean
    public ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean() {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setCorePoolSize(corePoolSize);
        factoryBean.setMaxPoolSize(maxPoolSize);
        factoryBean.setQueueCapacity(queueCapacity);
        factoryBean.setKeepAliveSeconds(keepAliveSecond);
        factoryBean.setThreadNamePrefix("task-threadPoolExecutor-");
        return factoryBean;
    }
}
