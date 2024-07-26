package com.finshark.backend.services;

import java.util.concurrent.CompletableFuture;

import com.finshark.backend.entities.Stock;

public interface FMPService {
    CompletableFuture<Stock> findStockBySymbol(String symbol);
}
