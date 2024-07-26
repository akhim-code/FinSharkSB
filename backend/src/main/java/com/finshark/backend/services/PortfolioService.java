package com.finshark.backend.services;

import java.util.List;
import com.finshark.backend.entities.Stock;

public interface PortfolioService {
        List<Stock> findAppUserPortfolio(Long appUserId, int pageNo, int pageSize);
        void addStockToPortfolio(Long id, String symbol);
        void removeStockFromPortfolio(Long id, String symbol);
}
