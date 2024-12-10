package com.realtimeeventticketingsystem.service;


import com.realtimeeventticketingsystem.dto.response.ResponseSystemConfigDto;

public interface SystemConfigureService {
    int findConfigValue(String configKey);
    ResponseSystemConfigDto findAllConfig();
    void updateConfigValue(String configKey, int configValue);
}
