package com.realtimeeventticketingsystem.repo;

import com.realtimeeventticketingsystem.entity.SalesLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SalesLogRepo extends JpaRepository<SalesLog, Integer> {
    @Query(value = "SELECT * FROM sales_log WHERE log LIKE %?1% OR date_time LIKE %?1% ORDER BY date_time DESC",
            nativeQuery = true)
    Page<SalesLog> findAllWithSearchText(String searchText, Pageable pageable);

    @Query(value = "SELECT * FROM sales_log ORDER BY sale_id DESC", nativeQuery = true)
    Page<SalesLog> findAllWithoutSearchText(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM sales_log WHERE log LIKE %?1% OR date_time LIKE %?1%",
            nativeQuery = true)
    long countAllWithSearchText(String searchText);
}
