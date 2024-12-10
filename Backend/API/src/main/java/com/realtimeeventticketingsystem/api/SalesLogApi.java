package com.realtimeeventticketingsystem.api;

import com.realtimeeventticketingsystem.service.SalesLogService;
import com.realtimeeventticketingsystem.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/saleslog")
@RequiredArgsConstructor
public class SalesLogApi {

    private final SalesLogService salesLogService;

    // Get customer list
    @GetMapping("/search")
    public ResponseEntity<StandardResponse> findAllWithText(
            @RequestParam String search_text,
            @RequestParam int page,
            @RequestParam int size
    ){
        return new ResponseEntity<>(new StandardResponse(200,
                "Successfully search Sales Logs",
                salesLogService.findAll(search_text, page, size)), HttpStatus.OK);
    }

    // Get customer list
    @GetMapping("/")
    public ResponseEntity<StandardResponse> findAllWithoutText(
            @RequestParam int page,
            @RequestParam int size
    ){
        return new ResponseEntity<>(new StandardResponse(200,
                "Successfully search Sales Logs",
                salesLogService.findAll(page, size)), HttpStatus.OK);
    }
}
