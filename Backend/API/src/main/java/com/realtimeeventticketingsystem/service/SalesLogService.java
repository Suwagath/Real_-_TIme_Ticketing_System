package com.realtimeeventticketingsystem.service;

import com.realtimeeventticketingsystem.dto.response.paginate.SalesLogPaginate;


public interface SalesLogService {
    SalesLogPaginate findAll(String searchText, int page, int size);
    SalesLogPaginate findAll(int page, int size);
}
