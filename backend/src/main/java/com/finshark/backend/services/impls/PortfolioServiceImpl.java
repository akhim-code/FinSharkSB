package com.finshark.backend.services.impls;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.finshark.backend.entities.AppUser;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.exceptions.AppException;
import com.finshark.backend.repositories.AppUserRepository;
import com.finshark.backend.repositories.StocksRepository;
import com.finshark.backend.services.PortfolioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService{
    
    private final StocksRepository stocksRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Stock> findAppUserPortfolio(Long appUserId, int pageNo, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);

        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        List<Stock> stocks = appUser.getStocks();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), stocks.size());

        List<Stock> pageContent = stocks.subList(start, end);
        PageImpl<Stock> page = new PageImpl<>(pageContent, pageRequest, pageContent.size());

        return page.getContent();
    }

    @Override
    public void addStockToPortfolio(Long appUserId, String symbol) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        List<Stock> stocks = appUser.getStocks();
        Stock stock = stocksRepository.findStockBySymbol(symbol);

        if (stock == null) {
            throw new AppException("Stock not found", HttpStatus.NOT_FOUND);
        }

        if (stocks.contains(stock)) {
            throw new AppException("Stock already in portfolio", HttpStatus.BAD_REQUEST);
        }

        stocks.add(stock);
        appUser.setStocks(stocks);
        appUserRepository.save(appUser);
    }

    @Override
    public void removeStockFromPortfolio(Long appUserId, String symbol) {
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        List<Stock> stocks = appUser.getStocks();
        Stock stock = stocksRepository.findStockBySymbol(symbol);

        if (stock == null) {
            throw new AppException("Stock not found", HttpStatus.NOT_FOUND);
        }

        if (!stocks.contains(stock)) {
            throw new AppException("Stock not in portfolio", HttpStatus.BAD_REQUEST);
        }

        stocks.remove(stock);
        appUser.setStocks(stocks);
        appUserRepository.save(appUser);
    }
}
