package com.finshark.backend.services;

import java.util.List;

import com.finshark.backend.dtos.stock.StockDto;

public interface PortfolioService {
        List<StockDto> findAppUserPortfolio(Long appUserId, int pageNo, int pageSize);
        void addStockToPortfolio(Long id, String symbol);
        void removeStockFromPortfolio(Long id, String symbol);
}
