package com.intuit.profileservice.service;

public interface RateUtil {
    Boolean getRate(String customerId, String action);
    Boolean updateRate(String customerId, String action);
}
