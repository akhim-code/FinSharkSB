package com.finshark.backend.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finshark.backend.entities.Stock;

public interface StocksRepository extends JpaRepository<Stock, Long>{

    Stock findStockBySymbol(String symbol);

    @Query("SELECT s FROM Stock s WHERE s.companyName LIKE CONCAT('%', :query, '%')")
    List<Stock> searchStocks(String query, Sort sort);
}
