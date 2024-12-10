package com.realtimeeventticketingsystem.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SystemConfigRequestDto {
    private int total_tickets;
    private int ticket_release_rate;
    private int customer_retrieval_rate;
    private int max_ticket_capacity;
}
