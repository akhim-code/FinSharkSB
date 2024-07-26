package com.finshark.backend.services;

import java.util.List;

import com.finshark.backend.dtos.stock.CreateStockDto;
import com.finshark.backend.dtos.stock.StockDto;
import com.finshark.backend.dtos.stock.UpdateStockDto;
import com.finshark.backend.entities.Stock;

public interface StocksService {
    List<StockDto> findAllStocks(int pageNo, int pageSize);
    StockDto findStockById(Long stockId);
    List<StockDto> searchStocks(String query, int pageNo, int pageSize);
    Stock saveStock(CreateStockDto stockDto);
    UpdateStockDto updateStock(Long stockId, UpdateStockDto stockDto);
    void delete(Long stockId);

}