package com.intuit.profileservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Custom implementation of {@link AsyncUncaughtExceptionHandler} for handling uncaught exceptions
 * that occur during the execution of asynchronous methods.
 */
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    // Logger for capturing and logging exceptions
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * Handles uncaught exceptions that occur during the execution of asynchronous methods.
     *
     * @param ex     The uncaught exception.
     * @param method The method that was invoked asynchronously.
     * @param params The parameters passed to the asynchronous method.
     */
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        // Log an error message containing information about the async method and the exception
        logger.error("Error in async method {} with error: {}", method.getName(), ex.getMessage());

        // Additional handling logic can be added here, such as sending notifications or taking specific actions.
    }
}
