package com.realtimeeventticketingsystem.api;

import com.realtimeeventticketingsystem.dto.request.SystemConfigRequestDto;
import com.realtimeeventticketingsystem.service.SystemConfigureService;
import com.realtimeeventticketingsystem.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/configuresystemparameters")
@RequiredArgsConstructor
public class ConfigureSystemParametersApi {

    private final SystemConfigureService systemConfigureService;

    // Get configure system parameters status
    @GetMapping("/status")
    public ResponseEntity<StandardResponse> systemStatus() {
        return new ResponseEntity<>(new StandardResponse(200,
                "Configure System Parameters Status",
                systemConfigureService.findAllConfig()), HttpStatus.OK);
    }

    // Update configure system parameters
    @PatchMapping("/update")
    public ResponseEntity<StandardResponse> updateSystemConfigs(
            @RequestBody SystemConfigRequestDto systemConfigRequestDto) {

        int maxTicketCapacity = systemConfigureService.findConfigValue("max_ticket_capacity");

        if (systemConfigRequestDto.getTotal_tickets() <= 0
                || maxTicketCapacity < systemConfigRequestDto.getTotal_tickets()) {
            return new ResponseEntity<>(new StandardResponse(400,
                    "Invalid total_tickets it must be between 1 and Max Ticket Capacity " + maxTicketCapacity,
                    null),HttpStatus.BAD_REQUEST);
        }

        if (systemConfigRequestDto.getTicket_release_rate() <= 0
                || systemConfigRequestDto.getTicket_release_rate() > 300) {
            return new ResponseEntity<>(new StandardResponse(400,
                    "Invalid ticket_release_rate it must be between 1s and 300s",
                    null),HttpStatus.BAD_REQUEST);
        }

        if (systemConfigRequestDto.getCustomer_retrieval_rate() <= 0
                || systemConfigRequestDto.getCustomer_retrieval_rate() > 300) {
            return new ResponseEntity<>(new StandardResponse(400,
                    "You must specify customer_retrieval_rate it must be between 1s and 300s",
                    null),HttpStatus.BAD_REQUEST);
        }

        if (systemConfigRequestDto.getMax_ticket_capacity() <= 0) {
            return new ResponseEntity<>(new StandardResponse(400,
                    "You must specify max_ticket_capacity greater than 1",
                    null),HttpStatus.BAD_REQUEST);
        }

        systemConfigureService.updateConfigValue("total_tickets",
                systemConfigRequestDto.getTotal_tickets());
        systemConfigureService.updateConfigValue("ticket_release_rate",
                systemConfigRequestDto.getTicket_release_rate());
        systemConfigureService.updateConfigValue("customer_retrieval_rate",
                systemConfigRequestDto.getCustomer_retrieval_rate());
        systemConfigureService.updateConfigValue("max_ticket_capacity",
                systemConfigRequestDto.getMax_ticket_capacity());


        return new ResponseEntity<>(new StandardResponse(200,
                "Successfully update system configs",
                null), HttpStatus.OK);
    }

}
