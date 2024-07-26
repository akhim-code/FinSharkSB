package com.finshark.backend.mappers;

import com.finshark.backend.dtos.stock.CreateStockDto;
import com.finshark.backend.dtos.stock.FMPStockDto;
import com.finshark.backend.dtos.stock.StockDto;
import com.finshark.backend.dtos.stock.UpdateStockDto;
import com.finshark.backend.entities.Stock;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StocksMapper {
    static StockDto toStockDto(Stock stock) {
        StockDto stockDto = new StockDto();
        stockDto.setId(stock.getId());
        stockDto.setSymbol(stock.getSymbol());
        stockDto.setCompanyName(stock.getCompanyName());
        stockDto.setPurchase(stock.getPurchase());
        stockDto.setDividend(stock.getDividend());
        stockDto.setIndustry(stock.getIndustry());
        stockDto.setMarketCap(stock.getMarketCap());
        stockDto.setCommentDtos(CommentsMapper.toCommentDtos(stock.getComments()));
        return stockDto;
    }

    UpdateStockDto toUpdateStockDto(Stock stock);

    static Stock toStock(CreateStockDto createStockDto) {
        Stock stock = new Stock();
        stock.setSymbol(createStockDto.getSymbol());
        stock.setCompanyName(createStockDto.getCompanyName());
        stock.setPurchase(createStockDto.getPurchase());
        stock.setDividend(createStockDto.getDividend());    
        stock.setIndustry(createStockDto.getIndustry());
        stock.setMarketCap(createStockDto.getMarketCap());

        return stock;
    }

    static Stock toStockFromFMPStockDto(FMPStockDto fmpStockDto) {
        Stock stock = new Stock();
        stock.setSymbol(fmpStockDto.getSymbol());
        stock.setCompanyName(fmpStockDto.getCompanyName());
        stock.setPurchase(fmpStockDto.getPrice());
        stock.setDividend(fmpStockDto.getLastDiv());
        stock.setIndustry(fmpStockDto.getIndustry());
        stock.setMarketCap(fmpStockDto.getMktCap());

        return stock;    
    }


}
