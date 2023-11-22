package com.intuit.profileservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * Configuration class for enabling asynchronous processing in a Spring application.
 * Annotating a class with {@code @Configuration} indicates that this class provides bean definitions
 * for the application context.
 * Enabling asynchronous processing is achieved by using the {@code @EnableAsync} annotation.
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Configuration parameters for the ThreadPoolExecutor
    int corePoolSize = 10;
    int maximumPoolSize = 10;
    long keepAliveTime = 500; // milliseconds
    TimeUnit unit = TimeUnit.MILLISECONDS;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

    /**
     * Bean definition for the asynchronous task executor.
     *
     * @return Executor representing a ThreadPoolExecutor for handling asynchronous tasks.
     */
    @Bean
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

}
