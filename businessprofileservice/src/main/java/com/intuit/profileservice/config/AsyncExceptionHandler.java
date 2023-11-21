package com.intuit.profileservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        logger.error("Error in async method {} having error {}", method.getName(), ex.getMessage());
    }
}
