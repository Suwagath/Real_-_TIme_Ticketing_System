package com.realtimeeventticketingsystem.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "sales_log")
public class SalesLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private int id;

    @Column(name = "date_time", nullable = false)
    private String timeAndDate;

    @Column(nullable = false)
    private String log;
}
