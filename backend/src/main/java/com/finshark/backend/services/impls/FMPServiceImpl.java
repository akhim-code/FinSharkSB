package com.finshark.backend.services.impls;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finshark.backend.dtos.stock.FMPStockDto;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.exceptions.FMPException;
import com.finshark.backend.mappers.StocksMapper;
import com.finshark.backend.services.FMPService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FMPServiceImpl implements FMPService {

    @Value("${api-key}")
    private String apiKey;
    private HttpClient httpClient;
    
    @Override
    public CompletableFuture<Stock> findStockBySymbol(String symbol) {
        httpClient = HttpClient.newHttpClient();
        String url = String.format("https://financialmodelingprep.com/api/v3/profile/%s?apikey=%s", symbol, apiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        
        return  httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApplyAsync(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            FMPStockDto[] stocks = mapper.readValue(response.body(), FMPStockDto[].class);
                            if (stocks.length > 0 && stocks[0] != null) {
                                return StocksMapper.toStockFromFMPStockDto(stocks[0]);
                            }
                        } catch (Exception e) {
                            throw new FMPException(e);
                        }
                    }
                    return null;
                }
                );
    }
    
}
