package com.realtimeeventticketingsystem.api;

import com.realtimeeventticketingsystem.dto.request.SystemStatusRequestDto;
import com.realtimeeventticketingsystem.service.SystemConfigureService;
import com.realtimeeventticketingsystem.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/configuresystem")
@RequiredArgsConstructor
public class ConfigureSystemApi {

    private final SystemConfigureService systemConfigureService;

    // Get configure system status
    @GetMapping("/status")
    public ResponseEntity<StandardResponse> systemStatus() {
        Map<String, Object> data = new HashMap<>();
        data.put("system_status", systemConfigureService.findConfigValue("system_status"));
        data.put("cli_status", systemConfigureService.findConfigValue("cli_status"));

        return new ResponseEntity<>(new StandardResponse(200,
                "Configure System Status",
                data), HttpStatus.OK);
    }

    // Update configure system status
    @PatchMapping("/update")
    public ResponseEntity<StandardResponse> updateSystemStatus(
            @RequestBody SystemStatusRequestDto systemStatusRequestDto) {

        if (systemStatusRequestDto.getSystem_status() != 1
                && systemStatusRequestDto.getSystem_status() != 2) {
            return new ResponseEntity<>(new StandardResponse(400,
                    "You must specify system_status start for 2 stop for 1",
                    null),HttpStatus.BAD_REQUEST);
        }

        systemConfigureService.updateConfigValue("system_status",
                systemStatusRequestDto.getSystem_status() - 1);

        return new ResponseEntity<>(new StandardResponse(200,
                "Successfully update system status",
                null), HttpStatus.OK);
    }

}
