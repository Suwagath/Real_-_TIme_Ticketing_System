package com.realtimeeventticketingsystem.service.impl;

import com.realtimeeventticketingsystem.dto.response.ResponseSalesLogDto;
import com.realtimeeventticketingsystem.dto.response.paginate.SalesLogPaginate;
import com.realtimeeventticketingsystem.entity.SalesLog;
import com.realtimeeventticketingsystem.repo.SalesLogRepo;
import com.realtimeeventticketingsystem.service.SalesLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SalesLogServiceImpl implements SalesLogService {

    private final SalesLogRepo salesLogRepo;


    @Override
    public SalesLogPaginate findAll(String searchText, int page, int size) {
        return SalesLogPaginate.builder()
                .data_list(salesLogRepo.findAllWithSearchText(searchText, PageRequest.of(page, size))
                        .stream().map(this::getResponseSalesLogDto).toList())
                .log_count(salesLogRepo.countAllWithSearchText(searchText))
                .build();
    }

    @Override
    public SalesLogPaginate findAll(int page, int size) {
        return SalesLogPaginate.builder()
                .data_list(salesLogRepo.findAllWithoutSearchText(PageRequest.of(page, size))
                        .stream().map(this::getResponseSalesLogDto).toList())
                .log_count(salesLogRepo.countAllWithSearchText(""))
                .build();
    }

    private ResponseSalesLogDto getResponseSalesLogDto(SalesLog salesLog) {
        return ResponseSalesLogDto.builder()
                .id(salesLog.getId())
                .timeAndDate(salesLog.getTimeAndDate())
                .log(salesLog.getLog())
                .build();
    }
}
