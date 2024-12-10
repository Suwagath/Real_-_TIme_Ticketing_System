package com.realtimeeventticketingsystem.service.impl;

import com.realtimeeventticketingsystem.dto.response.ResponseSystemConfigDto;
import com.realtimeeventticketingsystem.repo.SystemConfigureRepo;
import com.realtimeeventticketingsystem.service.SystemConfigureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SystemConfigureServiceImpl implements SystemConfigureService {

    private final SystemConfigureRepo systemConfigureRepo;

    @Override
    public int findConfigValue(String configKey) {
        return systemConfigureRepo.findByConfigKey(configKey);
    }

    @Override
    public ResponseSystemConfigDto findAllConfig() {
        return new ResponseSystemConfigDto(
                systemConfigureRepo.findByConfigKey("total_tickets"),
                systemConfigureRepo.findByConfigKey("ticket_release_rate"),
                systemConfigureRepo.findByConfigKey("customer_retrieval_rate"),
                systemConfigureRepo.findByConfigKey("max_ticket_capacity"));
    }

    @Override
    @Transactional
    public void updateConfigValue(String configKey, int configValue) {
        systemConfigureRepo.updateConfigValueUsingKey(configKey, configValue);
    }
}
