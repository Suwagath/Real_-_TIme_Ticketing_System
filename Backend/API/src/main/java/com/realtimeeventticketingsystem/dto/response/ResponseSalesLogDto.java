package com.realtimeeventticketingsystem.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseSalesLogDto {
    private int id;
    private String timeAndDate;
    private String log;
}
