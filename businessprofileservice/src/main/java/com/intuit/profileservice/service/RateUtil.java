package com.intuit.profileservice.service;

public interface RateUtil {
    Boolean getRate(String customerId, String action);
    void updateRate(String customerId, String action);
}
