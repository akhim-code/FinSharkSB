package com.finshark.backend.dtos.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStockDto {

    private String symbol; 
    private double purchase;
    private double dividend;
    private String industry;
    private Long marketCap;
    private String companyName;
}
