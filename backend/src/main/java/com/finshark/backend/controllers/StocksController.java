package com.finshark.backend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finshark.backend.dtos.stock.CreateStockDto;
import com.finshark.backend.dtos.stock.StockDto;
import com.finshark.backend.dtos.stock.UpdateStockDto;
import com.finshark.backend.services.StocksService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StocksController {

    private final StocksService stocksService; 

    @GetMapping("/api/stocks/")
    public ResponseEntity<List<StockDto>> findAllStocks(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(stocksService.findAllStocks(pageNo, pageSize));
    }

    @GetMapping("/api/stocks/{stockId}")
    public ResponseEntity<StockDto> findStockById(@PathVariable("stockId") Long stockId) {
        return ResponseEntity.ok(stocksService.findStockById(stockId));
    }

    @GetMapping("/api/stocks/search")
    public ResponseEntity<List<StockDto>> searchStock(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, @RequestParam(value = "query") String query) {
        return ResponseEntity.ok(stocksService.searchStocks(query, pageNo, pageSize));
    }

    @PostMapping("/api/stocks/create")
    public ResponseEntity<CreateStockDto> saveStock(@RequestBody CreateStockDto createStockDto) {
        URI uri = URI.create("/api/stocks/" + stocksService.saveStock(createStockDto).getId());
        return ResponseEntity.created(uri).body(createStockDto);
    }

    @PutMapping("api/stocks/{stockId}/update")
    public ResponseEntity<UpdateStockDto> updateStock(@PathVariable("stockId") Long stockId, @RequestBody UpdateStockDto updateStockDto) {
        return ResponseEntity.ok(stocksService.updateStock(stockId, updateStockDto));
    }

    @DeleteMapping("api/stocks/{stockId}/delete")
    public ResponseEntity<Void> deleteStock(@PathVariable("stockId") Long stockId) {
        stocksService.delete(stockId);
        return ResponseEntity.noContent().build();
    }

}
