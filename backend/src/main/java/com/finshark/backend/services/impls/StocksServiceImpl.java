package com.finshark.backend.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.finshark.backend.dtos.stock.CreateStockDto;
import com.finshark.backend.dtos.stock.StockDto;
import com.finshark.backend.dtos.stock.UpdateStockDto;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.exceptions.AppException;
import com.finshark.backend.mappers.StocksMapper;
import com.finshark.backend.repositories.StocksRepository;
import com.finshark.backend.services.StocksService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StocksServiceImpl implements StocksService{
    
        private final StocksRepository stocksRepository;
        private final StocksMapper stocksMapper;
    
        @Override
        public List<StockDto> findAllStocks(int pageNo, int pageSize) {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Stock> page = stocksRepository.findAll(pageable);
            List<Stock> stocks = page.getContent();
            return stocks.stream().map(StocksMapper::toStockDto).collect(Collectors.toList());
        }
    
        @Override
        public StockDto findStockById(Long stockId) {
            return StocksMapper.toStockDto(stocksRepository.findById(stockId).orElseThrow(() -> new AppException("Stock not found", HttpStatus.NOT_FOUND)));
        }

        @Override 
        public List<StockDto> searchStocks(String query, int pageNo, int pageSize) {
            Pageable pageRequest = PageRequest.of(pageNo, pageSize);

            List<Stock> stocks = stocksRepository.searchStocks(query, Sort.by(Sort.Direction.DESC, "title"));
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), stocks.size());

            List<Stock> pageContent = stocks.subList(start, end);
            PageImpl<Stock> page = new PageImpl<>(pageContent, pageRequest, pageContent.size());

            List<Stock> content = page.getContent();
            return content.stream().map(StocksMapper::toStockDto).collect(Collectors.toList());
        }

        @Override
        public Stock saveStock(CreateStockDto createStockDto) {
            Stock stock = stocksRepository.findStockBySymbol(createStockDto.getSymbol());
            if (stock != null) {
                throw new AppException("Stock already exists", HttpStatus.BAD_REQUEST);
            }
            return stocksRepository.save(StocksMapper.toStock(createStockDto));
        }

        @Override
        public UpdateStockDto updateStock(Long stockId, UpdateStockDto stockDto) {
        Stock stock = stocksRepository.findById(stockId).orElseThrow(() -> new AppException("Stock not found", HttpStatus.NOT_FOUND));
            stock.setSymbol(stockDto.getSymbol());
            stock.setCompanyName(stockDto.getCompanyName());
            stock.setPurchase(stockDto.getPurchase());
            stock.setDividend(stockDto.getDividend());
            stock.setIndustry(stockDto.getIndustry());
            stock.setMarketCap(stockDto.getMarketCap());
            stocksRepository.save(stock);

            return stocksMapper.toUpdateStockDto(stock);
        }

        @Override
        public void delete(Long stockId) {
            Stock stock = stocksRepository.findById(stockId).orElseThrow(() -> new AppException("Stock not found", HttpStatus.NOT_FOUND));
            stocksRepository.delete(stock);
        }

}
