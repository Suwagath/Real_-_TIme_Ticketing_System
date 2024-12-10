package com.realtimeeventticketingsystem.repo;

import com.realtimeeventticketingsystem.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface SystemConfigureRepo extends JpaRepository<SystemConfig, Integer> {
    @Query(value = "SELECT config_value FROM system_config WHERE config_key = ?1", nativeQuery = true)
    int findByConfigKey(String config_key);

    @Modifying
    @Query(value = "UPDATE system_config SET config_value = ?2 WHERE config_key = ?1", nativeQuery = true)
    void updateConfigValueUsingKey(String config_key, int config_value);
}
