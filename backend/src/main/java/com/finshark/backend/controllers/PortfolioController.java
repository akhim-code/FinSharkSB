package com.finshark.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.repositories.StocksRepository;
import com.finshark.backend.services.FMPService;
import com.finshark.backend.services.PortfolioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final FMPService fmpService;
    private final StocksRepository stocksRepository;

    @GetMapping("/api/portfolio/")
    public ResponseEntity<List<Stock>> findAppUserPortfolio(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(portfolioService.findAppUserPortfolio(((AppUserDto) authentication.getPrincipal()).getId(), pageNo, pageSize));
    }

    @PostMapping("/api/portfolio/")
    public ResponseEntity<Void> addStockToPortfolio(@RequestParam("symbol") String symbol) {
        symbol = symbol.toUpperCase();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Stock stock = stocksRepository.findStockBySymbol(symbol);
        if (stock == null)
        {
            stock = fmpService.findStockBySymbol(symbol).join();
            if (stock == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                stocksRepository.save(stock);
            }
        }
        portfolioService.addStockToPortfolio(((AppUserDto) authentication.getPrincipal()).getId(), symbol);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/portfolio/")
    public ResponseEntity<Void> removeStockFromPortfolio(@RequestParam("symbol") String symbol) {
        symbol = symbol.toUpperCase();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        portfolioService.removeStockFromPortfolio(((AppUserDto) authentication.getPrincipal()).getId(), symbol);
        return ResponseEntity.ok().build();
    }
    
}
