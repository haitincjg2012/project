package com.ph.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @项目：phshopping-parent
 * @描述：
 * @作者： Mr.chang
 * @创建时间：2017/6/27
 * @Copyright @2017 by Mr.chang
 */
@Configuration
@EnableAsync
public class TaskExecuterServiceConfig {

    private int corePoolSize = 10;
    /** 线程池最大个数. */
    private int maxPoolSize = 200;
    /** 线程池队列个数. */
    private int queueCapacity = 10;

    @Bean
    public Executor AsyncTask() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
