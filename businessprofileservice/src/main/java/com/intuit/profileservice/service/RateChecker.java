package com.intuit.profileservice.service;

public interface RateChecker {
    Boolean getUpdateRate(String customerId, String action, boolean increment);
}
